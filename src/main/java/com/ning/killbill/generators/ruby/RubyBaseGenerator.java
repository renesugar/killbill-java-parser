package com.ning.killbill.generators.ruby;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.ning.killbill.com.ning.killbill.args.KillbillParserArgs.GENERATOR_MODE;
import com.ning.killbill.generators.ClientLibraryBaseGenerator;
import com.ning.killbill.generators.GeneratorException;
import com.ning.killbill.objects.ClassEnumOrInterface;

public abstract class RubyBaseGenerator extends ClientLibraryBaseGenerator {

    protected final static String REQUIRE_FILE_NAME = "require_gen.rb";

    protected void generateRubyRequireFile(final List<ClassEnumOrInterface> classes, final File outputDir, final GENERATOR_MODE mode) throws GeneratorException {
        final File output = new File(outputDir, getRequireFileName());

        writeLicense(output);
        try {
            final Writer w = new FileWriter(output, true);
            writeHeader(w);
            for (ClassEnumOrInterface cur : classes) {
                w.write("require '" + getRequirePrefix(mode) + "/" + createFileName(cur.getName(), false) + "'\n");
            }
            w.flush();
            w.close();
        } catch (IOException e) {
            throw new GeneratorException("Failed to create require file", e);
        }
    }

    protected void writeHeader(final Writer w) throws IOException {
        w.write("\n");
        w.write("\n");
        w.write("#\n");
        w.write("#                       DO NOT EDIT!!!\n");
        w.write("#    File automatically generated by killbill-java-parser (git@github.com:killbill/killbill-java-parser.git)\n");
        w.write("#\n");
        w.write("\n");
        w.write("\n");
    }


}