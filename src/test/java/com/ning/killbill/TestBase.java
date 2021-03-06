package com.ning.killbill;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.ning.killbill.objects.Field;
import com.ning.killbill.objects.MethodOrDecl;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.BeforeClass;

import com.ning.killbill.objects.ClassEnumOrInterface;

import com.google.common.io.Resources;


public abstract class TestBase {

    protected KillbillListener listener;

    @BeforeClass(groups = "fast")
    public void setup() throws IOException, URISyntaxException {
        ANTLRInputStream input = new ANTLRFileStream(getResourceFileName());
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        parser.setBuildParseTree(true);
        RuleContext tree = parser.compilationUnit();

        ParseTreeWalker walker = new ParseTreeWalker();
        listener = new KillbillListener();
        walker.walk(listener, tree);
    }

    protected MethodOrDecl getMethod(final String name, List<MethodOrDecl> methods) {
        for (MethodOrDecl cur : methods) {
            if (cur.getName().equals(name)) {
                return cur;
            }
        }
        return null;
    }


    protected Field getField(final String name, List<Field> fields) {
        for (Field cur : fields) {
            if (cur.getName().equals(name)) {
                return cur;
            }
        }
        return null;
    }


    protected ClassEnumOrInterface getClassEnumOrInterface(final String name, List<ClassEnumOrInterface> input) {
        for (ClassEnumOrInterface cur : input) {
            if (cur.getName().equals(name)) {
                return cur;
            }
        }
        return null;
    }

    protected boolean isSuperInterfaceDefined(String ifceName, List<String> ifces) {
        for (String cur : ifces) {
            if (cur.equals(ifceName)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isEnumValueDefined(String value, List<String> enumValues) {
        for (String cur : enumValues) {
            if (cur.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public String getResourceFileName() throws IOException, URISyntaxException {
        URL resource = Resources.getResource(getResourceName());
        File resourceFile = new File(resource.toURI());
        return resourceFile.getAbsolutePath();
    }

    public abstract String getResourceName();
}
