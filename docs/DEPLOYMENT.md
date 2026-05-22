# Deploy Guide — Publishing to Maven Central

> **⚠️ Important:** This guide explains how to publish *this library* (SimpleCalendar) to Maven Central using the Sonatype Central Portal. If you forked this project and want to publish your own version under your own namespace, follow the same steps but replace the group ID and metadata with your own.

---

## Table of Contents

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Step 1 — One-time Central Portal setup](#step-1--one-time-central-portal-setup)
- [Step 2 — GPG signing key](#step-2--gpg-signing-key)
- [Step 3 — Configure credentials (local)](#step-3--configure-credentials-local)
- [Step 4 — Publish a release](#step-4--publish-a-release)
- [Step 5 — GitHub Actions (CI / automated)](#step-5--github-actions-ci--automated)
- [Troubleshooting](#troubleshooting)
- [Project files reference](#project-files-reference)

---

## Overview

This project uses the **vanniktech/gradle-maven-publish-plugin** which publishes via the **Sonatype Central Portal Publisher API** (the successor to OSSRH, which was sunset on June 30, 2025).

### How publishing works

| Step | Who | What |
|------|-----|------|
| 1 | Gradle plugin | Builds and signs artifacts, bundles them into a ZIP, uploads to Central Portal |
| 2 | Central Portal | Validates signatures, POM, checksums, metadata |
| 3 | You / plugin | Releases (publishes) the validated deployment |
| 4 | Central Portal | Syncs to Maven Central (`repo1.maven.org`) |

---

## Prerequisites

| Requirement | Notes |
|-------------|-------|
| A Sonatype Central Portal account | https://central.sonatype.com — sign in with your old OSSRH credentials |
| A verified namespace | `io.github.luisgamas` — already migrated from OSSRH |
| GPG key pair | For signing artifacts (required by Maven Central) |
| Java 17+ | The project targets Java 17 |
| Gradle 8.x | Wrapper included in the project |

---

## Step 1 — One-time Central Portal setup

### 1.1 Log in

Go to [central.sonatype.com](https://central.sonatype.com/) and sign in with your OSSRH username/password. If you don't remember your password, use the "Forgot password" link — **do not create a new account**, otherwise your namespace won't be linked.

### 1.2 Verify your namespace

1. Click your avatar → **View Namespaces**
2. Confirm `io.github.luisgamas` appears in the list
3. If it doesn't, click **Migrate namespace** and follow the prompts

### 1.3 Generate a Portal User Token

1. Go to **Settings → User Tokens**
2. Click **Generate Token**
3. Copy both the **username** and **password** shown — this is your machine credential for publishing. It is **not** the same as your login credentials.
   > Save these somewhere safe (e.g. a password manager). You will not be able to see the full token again.

---

## Step 2 — GPG signing key

Maven Central requires all artifacts to be signed with a GPG key.

### 2.1 Create a key pair (if you don't have one)

**Linux / macOS:**

```bash
gpg --full-generate-key
# Choose:
#   kind: RSA and RSA (default)
#   keysize: 4096
#   expiry: 0 (never)
#   Real name: Luis Donaldo Gamas
#   Email: your-email@example.com
#   Passphrase: choose a strong one
```

**Windows:**

```bash
# Download Gpg4win from https://gpg4win.org and install it
# Then use the same commands in PowerShell or cmd
gpg --full-generate-key
```

### 2.2 List your keys

```bash
gpg --list-secret-keys --keyid-format=long
```

Example output:
```
sec   rsa4096/ABC12345DEF67890 2023-01-01 [SC]
      XXXX...
uid                 [ultimate] Luis Donaldo Gamas <email@example.com>
```

The key ID is the 16-character hex string after the slash — in this example `ABC12345DEF67890`. The **last 8 characters** (`DEF67890`) are used as the `signing.keyId`.

### 2.3 Distribute the public key

Upload your public key to a keyserver so Maven Central can verify signatures:

```bash
gpg --keyserver keyserver.ubuntu.com --send-keys ABC12345DEF67890
```

### 2.4 Export the private key (for CI / in-memory signing)

```bash
gpg --export-secret-keys --armor ABC12345DEF67890
```

This outputs a long block starting with `-----BEGIN PGP PRIVATE KEY BLOCK-----`. Save this value — you'll need it for CI configuration.

---

## Step 3 — Configure credentials (local)

### 3.1 Copy the example file

There is a template at `gradle.properties.example` in the project root:

```bash
cp gradle.properties.example ~/.gradle/gradle.properties
```

### 3.2 Edit the file

Open `~/.gradle/gradle.properties` and fill in your values:

```properties
# Portal token credentials (from Step 1.3)
mavenCentralUsername=YOUR_PORTAL_TOKEN_USERNAME
mavenCentralPassword=YOUR_PORTAL_TOKEN_PASSWORD

# GPG signing — local file-based key
signing.keyId=DEF67890              # last 8 chars of your key ID
signing.password=YOUR_GPG_PASSPHRASE
signing.secretKeyRingFile=C:/Users/You/.gnupg/secring.gpg   # Windows
# signing.secretKeyRingFile=/Users/you/.gnupg/secring.gpg   # macOS
# signing.secretKeyRingFile=/home/you/.gnupg/secring.gpg    # Linux
```

> **Note about `secring.gpg`:** Modern GPG versions (2.x) no longer export the secret key ring by default. If the file doesn't exist, create it:
> ```bash
> gpg --export-secret-keys --armor ABC12345DEF67890 > ~/.gnupg/secring.gpg
> ```

### 3.3 Set the library version

Edit `gradle.properties` (project-level, safe to commit) and update:

```properties
VERSION_NAME=2.0.0
```

> **Important:** Do NOT include `v` prefix. Version must follow Maven semantic versioning (e.g. `2.0.0`, `2.0.1-SNAPSHOT`).

---

## Step 4 — Publish a release

### 4.1 Update the version

1. Edit `gradle.properties` → set `VERSION_NAME` to your release version
2. Commit and push:

```bash
git add gradle.properties
git commit -m "chore: bump version to 2.0.0"
git tag v2.0.0
git push --tags
```

> The tag name **must** match `v*` if you plan to use the GitHub Actions workflow.

### 4.2 Publish manually from your machine

**Option A — manual release (you click "Publish" in the web UI):**

```bash
./gradlew publishToMavenCentral
```

Then go to [central.sonatype.com/publishing/deployments](https://central.sonatype.com/publishing/deployments), find your deployment, and click **Publish**.

**Option B — automatic release (no clicks needed):**

```bash
./gradlew publishAndReleaseToMavenCentral
```

The plugin will wait for validation and automatically release the deployment.

### 4.3 Verify

After 10–30 minutes, your library will be available at:

```
https://repo1.maven.org/maven2/io/github/luisgamas/simplecalendar/<VERSION>/
```

---

## Step 5 — GitHub Actions (CI / automated)

The project includes a CI workflow at `.github/workflows/publish.yml` that automatically publishes whenever a `v*` tag is pushed.

### 5.1 Set repository secrets

Go to your GitHub repository → **Settings → Secrets and variables → Actions** and add these secrets:

| Secret name | Value |
|-------------|-------|
| `MAVEN_CENTRAL_USERNAME` | Portal token username (Step 1.3) |
| `MAVEN_CENTRAL_PASSWORD` | Portal token password (Step 1.3) |
| `SIGNING_KEY` | ASCII-armored private key (Step 2.4) — multiline |
| `SIGNING_KEY_ID` | Last 8 chars of GPG key ID (e.g. `DEF67890`) |
| `SIGNING_KEY_PASSPHRASE` | GPG passphrase |

### 5.2 Trigger a release

```bash
git tag v2.0.0
git push --tags
```

The workflow will:
1. Check out the tag
2. Set up JDK 17
3. Run `./gradlew publishAndReleaseToMavenCentral`
4. All credentials are injected via environment variables (the plugin supports `ORG_GRADLE_PROJECT_*` env vars)

---

## Troubleshooting

### Deployment fails validation

Go to [central.sonatype.com/publishing/deployments](https://central.sonatype.com/publishing/deployments), open the failed deployment, and check the **Validation Results** column. Common issues:

| Error | Fix |
|-------|-----|
| `Missing required field: ...` | Check POM metadata in `gradle.properties` |
| `Signature validation failed` | GPG key did not sign correctly. Verify `signing.keyId` and passphrase |
| `Artifact is not signed` | Ensure `signAllPublications()` is in `build.gradle` |
| `POM file is invalid` | Run `./gradlew generatePomFileForReleasePublication` and inspect the generated POM |

### 401 Unauthorized when publishing

Your Portal token is incorrect. Generate a new one at [central.sonatype.com](https://central.sonatype.com) → Settings → User Tokens.

### "secring.gpg" file not found

Modern GPG versions don't create this file by default. Export your secret key manually:

```bash
gpg --export-secret-keys --armor ABC12345DEF67890 > ~/.gnupg/secring.gpg
```

### Publishing works locally but fails on CI

Make sure the `SIGNING_KEY` secret contains the **entire** ASCII-armored block including the `-----BEGIN PGP PRIVATE KEY BLOCK-----` and `-----END PGP PRIVATE KEY BLOCK-----` lines.

---

## Project files reference

| File | Purpose | Commit? |
|------|---------|---------|
| `build.gradle` (root) | Declares the `com.vanniktech.maven.publish` plugin | ✅ Yes |
| `SimpleCalendar/build.gradle` | Publishing DSL: target, signing, POM coordinates | ✅ Yes |
| `gradle.properties` | POM metadata (version, description, license, SCM, etc.) | ✅ Yes |
| `gradle.properties.example` | Template for local credentials (copy to `~/.gradle/`) | ✅ Yes |
| `.github/workflows/publish.yml` | CI workflow triggered by `v*` tags | ✅ Yes |
| `~/.gradle/gradle.properties` | **Local only** — credentials, NEVER commit | ❌ No |
