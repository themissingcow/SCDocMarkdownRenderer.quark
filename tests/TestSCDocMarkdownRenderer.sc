// SPDX-License-Identifier: GPL-3.0-or-later

TestSCDocMarkdownRenderer : UnitTest {

	var didSetUp = false;

	setUp {
		if(didSetUp.not) {

			if(SCDoc.didIndexDocuments.not) {
				SCDoc.indexAllDocuments;
			};

			didSetUp = true;
		}
	}

	test_markdown_output {

		var expectedFile;
		var expected;

		var testPath = TestSCDocMarkdownRenderer.filenameSymbol().asString().dirname;

		var node = SCDoc.parseFileFull(testPath +/+ "SampleDoc.schelp");
		var doc = SCDocEntry(node, "Class/SampleDoc");

		var stream = CollStream("");
		SCDocMarkdownRenderer.renderOnStream(stream, doc, node);

		expectedFile = File(testPath +/+ "SampleDoc_expected.md", "r");
		expected = expectedFile.readAllString;
		expectedFile.close();

		this.assertEquals(stream.contents, expected);
		this.assertEquals(stream.collection.size, expected.size);
	}
}
