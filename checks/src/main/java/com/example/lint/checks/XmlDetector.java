package com.example.lint.checks;

 import static com.android.SdkConstants.ANDROID_URI;
        import static com.android.SdkConstants.ATTR_CONTENT_DESCRIPTION;
        import static com.android.SdkConstants.ATTR_HINT;
        import static com.android.SdkConstants.ATTR_LABEL;
        import static com.android.SdkConstants.ATTR_PROMPT;
        import static com.android.SdkConstants.ATTR_TEXT;
        import static com.android.SdkConstants.ATTR_TITLE;
        import com.android.annotations.NonNull;
        import com.android.resources.ResourceFolderType;
        import com.android.tools.lint.detector.api.Category;
        import com.android.tools.lint.detector.api.Implementation;
        import com.android.tools.lint.detector.api.Issue;
        import com.android.tools.lint.detector.api.LayoutDetector;
        import com.android.tools.lint.detector.api.Scope;
        import com.android.tools.lint.detector.api.Severity;
        import com.android.tools.lint.detector.api.XmlContext;
        import org.w3c.dom.Attr;
        import java.util.Arrays;
        import java.util.Collection;


/**
 * author: russell
 * time: 2019-12-11:22:38
 * describe：
 */


public class XmlDetector extends LayoutDetector {
    /** The main issue discovered by this detector */
    public static final Issue ISSUE = Issue.create(
            "xml_text_id", //$NON-NLS-1$
            "繁简转换",
            "请避免使用简体",
            Category.CORRECTNESS,
            10,
            Severity.ERROR,
            new Implementation(
                    XmlDetector.class,
                    Scope.RESOURCE_FILE_SCOPE));
    public XmlDetector() {
    }

    @Override
    public Collection<String> getApplicableAttributes() {
        return Arrays.asList(
                // Layouts
                ATTR_TEXT,
                ATTR_CONTENT_DESCRIPTION,
                ATTR_HINT,
                ATTR_LABEL,
                ATTR_PROMPT,
                // Menus
                ATTR_TITLE
        );
    }
    @Override
    public boolean appliesTo(@NonNull ResourceFolderType folderType) {
        return folderType == ResourceFolderType.LAYOUT || folderType == ResourceFolderType.MENU;
    }
    @Override
    public void visitAttribute(@NonNull XmlContext context, @NonNull Attr attribute) {
        String value = attribute.getValue();
        if (Text.isChinese(value)) {
            // Make sure this is really one of the android: attributes
            if (!ANDROID_URI.equals(attribute.getNamespaceURI())) {
                return;
            }
            context.report(ISSUE, attribute, context.getLocation(attribute),
                    "请避免使用简体");
        }
    }
}