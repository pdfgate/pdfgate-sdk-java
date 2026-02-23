# PDFGate's official Java SDK

[![Maven Central](https://img.shields.io/badge/maven--central-v31.3.0-blue)](https://mvnrepository.com/artifact/com.pdfgate/pdfgate)
[![JavaDoc](http://img.shields.io/badge/javadoc-reference-blue.svg)](https://pdfgate.github.io/pdfgate-sdk-java)
[![Build Status](https://github.com/pdfgate/pdfgate-sdk-java/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/pdfgate/pdfgate-sdk-java/actions?query=branch%3Amain)

PDFGate lets you generate, process, and secure PDFs via a simple API:

- HTML or URL to PDF
- Fillable forms
- Flatten, compress, watermark, protect PDFs
- Extract PDF form data
- Upload PDF files

📘 Documentation: https://pdfgate.com/documentation<br>
🔑 Dashboard & API keys: https://dashboard.pdfgate.com

## Table of Contents

- [Installation](#installation)
- [Quick start](#quick-start)
- [Sync & Async](#sync--async)
- [Responses](#responses)
- [Examples](#examples)
- [Development](#development)
- [Support](#support)
- [License](#license)

# Installation

## Requirements

We support LTS versions of the JDK. Currently, that's Java versions:

8 (1.8)
11
17
21
25

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "com.pdfgate:pdfgate:0.1.0"
```

### Maven users

Add this dependency to your project's POM:

```xml

<dependency>
    <groupId>com.pdfgate</groupId>
    <artifactId>pdfgate</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Others

If you are not using Gradle or Maven, you will need to manually install the following JARs:

1. The PDFGate JAR:

- Download the latest release version
  from [Maven Central](https://repo1.maven.org/maven2/com/pdfgate/pdfgate/0.1.0/pdfgate-0.1.0.jar)
- Current release version: 0.1.0

2. Google Gson:

- The PDFGate JAR builds and tests with Gson version 2.11.0
- Download from [Maven Central](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.11.0/gson-2.11.0.jar)
- We recommend using the same version of Gson if possible to guarantee compatibility, but you should be able to use any
  stable version of Gson that is 2.11.0 or newer

3. OkHttp:

- The PDFGate JAR builds and tests with OkHttp version 4.12.0
- Download
  from [Maven Central](https://repo1.maven.org/maven2/com/squareup/code/okhttp3/okhttp/4.12.0/okhttp-4.12.0.jar)
- We recommend using the same version of OkHttp if possible to guarantee compatibility, but you should be able to use
  any stable version of Gson that is 2.11.0 or newer

To use these JARs:

1. Download the JARs from the links provided above
2. Add the JARs to your project's classpath

# Quick start

```java
import com.pdfgate.GeneratePdfParams;
import com.pdfgate.GetFileParams;
import com.pdfgate.PdfGate;
import com.pdfgate.PdfGateDocument;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfGateExample {
  static void main(String[] args) {
    String apiKey = "test_123";
    PdfGate client = new PdfGate(apiKey);

    GeneratePdfParams params = GeneratePdfParams.builder()
        .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
        .build();

    PdfGateDocument document = client.generatePdf(params);

    Path filePath = Paths.get("output.pdf");
    try {
      byte[] fileBytes = client.getFile(GetFileParams.builder()
          .documentId(document.getId())
          .build());
      Files.write(filePath, fileBytes);
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }
}
```

# Sync & Async & Callbacks

The public API offers three ways to use any of the endpoints:

- sync: `PdfGate.generatePdf`
- async with futures: `PdfGate.generatePdfAsync`
- async with callbacks: `PdfGate.generatePdfCall`

### Sync

The method without any suffix is regular synchronous code, the call will return the result from the API:

```java
PdfGateDocument document = client.generatePdf(params);
```

### Async with futures

The method with an `Async` suffix allows you to work asynchronously with `CompletableFuture`s:

```java
import java.util.concurrent.CompletableFuture;

CompletableFuture<PdfGateDocument> pdfFileFuture = client.generatePdfAsync(params);
```

## Async with callbacks

Finally, the method with a `Call` suffix allows you to work with `Callback`s:

```java
CallJson call = client.generatePdfCall(params);

client.enqueue(call, new PdfGateCallback<>() {
  @Override
  public void onSuccess(okhttp3.Call call, PdfGateDocument value) {
    // success code
  }

  @Override
  public void onFailure(okhttp3.Call call, Throwable t) {
    // failure code
  }
});
```

# Responses

Most endpoints return a `PdfGateDocument` containing metadata including the `id` and optional `fileUrl`
when `preSignedUrlExpiresIn` is provided. To download the file bytes, call `getFile` with the document id.

# Examples

## Generate PDF

```java
GeneratePdfParams params = GeneratePdfParams.builder()
    .html("<h1>Hello from PDFGate!</h1>")
    .build();

PdfGateDocument document = client.generatePdf(params);
```

## Get document metadata

```java
GetDocumentParams params = GetDocumentParams.builder()
    .documentId(documentId)
    .build();

PdfGateDocument document = client.getDocument(params);
```

## Download a stored PDF file

```java
GetFileParams params = GetFileParams.builder()
    .documentId(documentId)
    .build();

byte[] fileContent = client.getFile(params);
Files.write(Paths.get("output.pdf"), fileContent);
```

## Upload a PDF file

```java
byte[] fileBytes = Files.readAllBytes(Paths.get("input.pdf"));

UploadFileParams uploadParams = UploadFileParams.builder()
    .file(new FileParam("input.pdf", fileBytes, "application/pdf"))
    .build();

PdfGateDocument uploadedDocument = client.uploadFile(uploadParams);
```

If you already have a public URL to a PDF, omit `file` and use `url` instead:

```java
UploadFileParams uploadParams = UploadFileParams.builder()
    .url("https://example.com/input.pdf")
    .build();

PdfGateDocument uploadedDocument = client.uploadFile(uploadParams);
```

## Flatten a PDF (make form-fields non-editable)

```java
FlattenPdfParams flattenParams = FlattenPdfParams.builder()
    .documentId(documentId)
    .build();

PdfGateDocument flattenedDocument = client.flattenPdf(flattenParams);
```

## Compress a PDF

```java
CompressPdfParams compressParams = CompressPdfParams.builder()
    .documentId(documentId)
    .build();

PdfGateDocument compressedDocument = client.compressPdf(compressParams);
```

## Watermark a PDF

```java
byte[] watermarkImage = Files.readAllBytes(Paths.get("watermark.jpg"));

WatermarkPdfParams watermarkParams = WatermarkPdfParams.builder()
    .documentId(documentId)
    .type(WatermarkPdfParams.WatermarkType.IMAGE)
    .watermark(new FileParam("watermark.jpg", watermarkImage, "image/jpeg"))
    .build();

PdfGateDocument watermarkedPdf = client.watermarkPdf(watermarkParams);
```

## Protect (encrypt) a PDF

```java
ProtectPdfParams protectParams = ProtectPdfParams.builder()
    .documentId(documentId)
    .userPassword(UUID.randomUUID().toString())
    .ownerPassword(UUID.randomUUID().toString())
    .build();

PdfGateDocument protectedDocument = client.protectPdf(protectParams);
```

## Extract PDF form fields values

```java
String htmlForm = "<form>"
    + "<input type='text' name='first_name' value='John'/>"
    + "<input type='text' name='last_name' value='Doe'/>"
    + "</form>";

GeneratePdfParams generateParams = GeneratePdfParams.builder()
    .html(htmlForm)
    .enableFormFields(true)
    .build();

PdfGateDocument document = client.generatePdf(generateParams);
String documentId = document.getId();

ExtractPdfFormDataParams extractParams = ExtractPdfFormDataParams.builder()
    .documentId(documentId)
    .build();

JsonObject response = client.extractPdfFormData(extractParams);
```

# Development

## Formattin & Linting

The project uses [checkstyle](https://checkstyle.sourceforge.io) with the Google checks. You
can find the XML in the repo root.

## Tests

Integration tests:

```sh
./gradlew test --tests "com.pdfgate.PdfGateTest"
```

Acceptance tests hit the PDFGate API so they are slower, and require an API key that is expected to be set as an env var
named `PDFGATE_API_KEY`. You can set it on your Bash/zsh/fish profile or inline as in:

```sh
PDFGATE_API_KEY="test_123" ./gradlew test --tests "com.pdfgate.PdfGateAcceptanceTest"
```

# Support

📧 Email: support@pdfgate.com<br>
📘 Docs: https://pdfgate.com/documentation

## License

`pdfgate-sdk-java` is distributed under the terms of the [MIT](https://spdx.org/licenses/MIT.html) license.
