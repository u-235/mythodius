package forms;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import microfont.MFont;
import microfont.events.MFontEvent;
import microfont.events.MFontListener;

import utils.resource.Resource;

@SuppressWarnings("serial")
public class PFontGeneral extends JPanel implements MFontListener
{
    Resource           res;
    MFont              mFont;
    boolean            readOnly;
    private JTextField vName;
    private JTextArea  vPrototype;

    public PFontGeneral(Resource res) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setResource(res);

        vName = new JTextField(24);
        vName.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (mFont == null) return;
                mFont.setName(vName.getText().trim());
            }

            @Override
            public void focusGained(FocusEvent e) {
            }
        });

        JPanel fr = new JPanel();
        fr.add(vName);
        Border border = new TitledBorder(res.getString(
                        "properties.tab.general.name", Resource.TEXT_NAME_KEY));
        fr.setBorder(border);
        vPrototype = new JTextArea(2, 24);
        vPrototype.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (mFont == null) return;
                mFont.setPrototype(vPrototype.getText().trim());
            }

            @Override
            public void focusGained(FocusEvent e) {
            }
        });

        vPrototype.setWrapStyleWord(true);
        vPrototype.setLineWrap(true);
        border = new TitledBorder(res.getString(
                        "properties.tab.general.prototype",
                        Resource.TEXT_NAME_KEY));
        JScrollPane scroll = new JScrollPane(vPrototype);
        scroll.setBorder(border);

        add(fr, 0);
        add(scroll, 1);
        updateReadOnly();
    }

    public Resource getResource() {
        return res;
    }

    public void setResource(Resource res) {
        this.res = res;
    }

    public MFont getMFont() {
        return mFont;
    }

    public void setMFont(MFont font) {
        if (mFont != null) mFont.removeListener(this);
        mFont = font;
        if (mFont != null) mFont.addListener(this);
        else return;
        vName.setText(mFont.getName());
        vPrototype.setText(mFont.getPrototype());
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        updateReadOnly();
    }

    void updateReadOnly() {
        vName.setEditable(!readOnly);
        vPrototype.setEditable(!readOnly);
    }

    @Override
    public void mFontEvent(MFontEvent change) {
        switch (change.getReason()) {
        case MFontEvent.FONT_NAME:
            break;
        case MFontEvent.FONT_PROTOTYPE:
            break;
        }
    }
}