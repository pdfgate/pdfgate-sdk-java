# PDFGate's official Java SDK

[![Maven Central](https://img.shields.io/badge/maven--central-v31.3.0-blue)](https://mvnrepository.com/artifact/com.pdfgate/pdfgate)
[![JavaDoc](http://img.shields.io/badge/javadoc-reference-blue.svg)](https://pdfgate.github.io/pdfgate-sdk-java)
[![Build Status](https://github.com/pdfgate/pdfgate-sdk-java/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/pdfgate/pdfgate-sdk-java/actions?query=branch%3Amain)

PDFGate lets you generate, process, and secure PDFs via a simple API:

- HTML or URL to PDF
- Fillable forms
- Flatten, compress, watermark, protect PDFs
- Extract PDF form data

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
import com.pdfgate.PdfGate;
import com.pdfgate.GeneratePdfParams;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfGateExample {
  static void main(String[] args) {
    String apiKey = "test_123";
    PdfGate client = new PdfGate(apiKey);

    GeneratePdfJsonParams params = GeneratePdfParams.builder()
        .html("<html><body><h1>Hello, PDFGate!</h1></body></html>")
        .buildWithFileResponse();

    byte[] pdfFile = client.generatePdf(params);

    Path filePath = Paths.get("output.pdf");
    try {
      Files.write(filePath, pdfFile);
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
byte[] pdfFile = client.generatePdf(params);
```

### Async with futures

The method with an `Async` suffix allows you to work asynchronously with `CompletableFuture`s:

```java
import java.util.concurrent.CompletableFuture;

CompletableFuture<byte[]> pdfFileFuture = client.generatePdfAsync(params);
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

The two response types for most endpoints are either the raw bytes of the
PDF file or the documents metadata that you can use to query or download later:

- file: this is represented by `byte[]` and it can be saved directly into a file.
- `PdfGateDocument`: this is an object that holds all the metadata of the file including its `id`, and a `fileUrl`
  to download it if it was requested by specifying `preSignedUrlExpiresIn` in the request.

# Examples

## Generate PDF

```java
GeneratePdfFileParams params = GeneratePdfParams.builder()
    .html("<h1>Hello from PDFGate!</h1>")
    .buildWithFileResponse();

byte[] pdf = client.generatePdf(params);
Files.write(Paths.get("output.pdf"), pdf);
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

## Flatten a PDF (make form-fields non-editable)

```java
FlattenPdfJsonParams flattenParams = FlattenPdfJsonParams.builder()
    .documentId(documentId)
    .buildWithJsonResponse();

PdfGateDocument flattenedDocument = client.flattenPdf(flattenParams);
```

## Compress a PDF

```java
CompressPdfJsonParams compressParams = CompressPdfParams.builder()
    .documentId(documentId)
    .buildWithJsonResponse();

PdfGateDocument compressedDocument = client.compressPdf(compressParams);
```

## Watermark a PDF

```java
byte[] pdfFile = Files.readAllBytes(Paths.get("input.pdf"));
byte[] watermarkImage = Files.readAllBytes(Paths.get("watermark.jpg"));

WatermarkPdfJsonParams watermarkParams = WatermarkPdfParams.builder()
    .file(new FileParam("input.pdf", pdfFile, "application/pdf"))
    .type(WatermarkPdfParams.WatermarkType.IMAGE)
    .watermark(new FileParam("watermark.jpg", watermarkImage, "image/jpeg"))
    .buildWithJsonResponse();

PdfGateDocument watermarkedPdf = client.watermarkPdf(watermarkParams);
```

## Protect (encrypt) a PDF

```java
ProtectPdfJsonParams protectParams = ProtectPdfParams.builder()
    .documentId(documentId)
    .userPassword(UUID.randomUUID().toString())
    .ownerPassword(UUID.randomUUID().toString())
    .buildWithJsonResponse();

PdfGateDocument protectedDocument = client.protectPdf(protectParams);
```

## Extract PDF form fields values

```java
String htmlForm = "<form>"
    + "<input type='text' name='first_name' value='John'/>"
    + "<input type='text' name='last_name' value='Doe'/>"
    + "</form>";

GeneratePdfJsonParams generateParams = GeneratePdfParams.builder()
    .html(htmlForm)
    .enableFormFields(true)
    .buildWithJsonResponse();

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
