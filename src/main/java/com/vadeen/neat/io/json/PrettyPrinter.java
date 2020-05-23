package com.vadeen.neat.io.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import java.io.IOException;

/**
 * JSON printer for getting easy to readGenome genome json files.
 *
 * Only indents the first level like:
 * {
 *     "connections: [
 *          { "", ... },
 *     ],
 *     ...
 * }
 */
public class PrettyPrinter extends DefaultPrettyPrinter {

    private static final int MAX_LEVEL = 3;

    public PrettyPrinter() {
        super();
        _arrayIndenter = new ArrayIndenter();
        _objectIndenter = new ObjectIndenter();
    }

    @Override
    public DefaultPrettyPrinter createInstance() {
        return new PrettyPrinter();
    }

    private static class ArrayIndenter implements Indenter {
        @Override
        public void writeIndentation(JsonGenerator g, int level) throws IOException {
            g.writeRaw("\n");
            for (int i = 0; i < level; i++)
                g.writeRaw("  ");
        }

        @Override
        public boolean isInline() {
            return false;
        }
    }

    private static class ObjectIndenter implements Indenter {
        @Override
        public void writeIndentation(JsonGenerator g, int level) throws IOException {
            if (level <= MAX_LEVEL) {
                g.writeRaw("\n");
                for (int i = 0; i < level; i++)
                    g.writeRaw("  ");
            }
            else
                g.writeRaw(" ");
        }

        @Override
        public boolean isInline() {
            return false;
        }
    }
}
