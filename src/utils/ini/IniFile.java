
package utils.ini;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import utils.config.RootNode;
import utils.config.ConfigNode;

public class IniFile extends RootNode {
    IniStyle style;

    public IniFile(String file) {
        super(file);
        load();
    }

    public IniFile(String file, IniStyle style) {
        super(file);
        this.style = style;
        load();
    }

    public IniFile(File file) {
        super(file);
        load();
    }

    public IniFile(File file, IniStyle style) {
        super(file);
        this.style = style;
        load();
    }

    @Override
    protected void loadS(InputStream in) {
        // TODO Auto-generated method stub
        try {
            Parser.parse(in, new IniHandler(), style);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void saveS(OutputStream out) {
        // TODO Auto-generated method stub

    }

    class IniHandler implements Handler {
        ConfigNode work;
        String     key;
        String     comment;

        IniHandler() {
            work = IniFile.this.root;
            comment = null;
        }

        @Override
        public void error(int state, int ch, int line, int col) {
            // TODO Auto-generated method stub

            System.out.println("error at " + line + " line in " + col
                            + " column, character code point = " + ch);
        }

        @Override
        public void comment(String com) {
            System.out.println("comments : " + com);
            // TODO Auto-generated method stub
            if (comment == null) comment = com;
            else comment = comment + "\n" + com;
        }

        @Override
        public void section(String sec) {
            System.out.println("section  : " + sec);
            work = work.root().node(sec);
            if (comment != null) {
                work.putComment(comment);
                comment = null;
            }
        }

        @Override
        public void key(String k) {
            System.out.println("key      : " + k);
            key = k;
        }

        @Override
        public void value(String value) {
            System.out.println("value    : " + value);
            if (key != null) {
                work.put(key, value);
                key = null;
                if (comment != null) {
                    work.putComment(key, comment);
                }
            }
            comment = null;
        }
    }
}
