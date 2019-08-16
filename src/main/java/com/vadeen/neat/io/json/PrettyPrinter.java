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

    private int maxLevel = 3;

    public PrettyPrinter(int levels) {
        super();
        _arrayIndenter = new ArrayIndenter();
        _objectIndenter = new ObjectIndenter();
    }

    @Override
    public DefaultPrettyPrinter createInstance() {
        return new PrettyPrinter(maxLevel);
    }

    private class ArrayIndenter implements Indenter {
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

    private class ObjectIndenter implements Indenter {
        @Override
        public void writeIndentation(JsonGenerator g, int level) throws IOException {
            if (level <= maxLevel) {
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
