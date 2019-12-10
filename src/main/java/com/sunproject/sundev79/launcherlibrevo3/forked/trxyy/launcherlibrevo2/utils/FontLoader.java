package com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.utils;

import com.sunproject.sundev79.launcherlibrevo3.forked.trxyy.launcherlibrevo2.LauncherConstants;

import javafx.scene.text.Font;

public class FontLoader
{
    public void loadFont(final LauncherConstants res, final String s) {
        Font.loadFont(this.getClass().getResourceAsStream(String.valueOf(LauncherConstants.getResourceLocation()) + s), 14.0);
    }
    
    public static void setFont(final String fontName, final float size) {
        Font.font(fontName, (double)size);
    }
    
    public static Font loadFont(final String fullFont, final String fontName, final float size) {
        Font.loadFont(FontLoader.class.getResourceAsStream(String.valueOf(LauncherConstants.getResourceLocation()) + fullFont), 14.0);
        final Font font = Font.font(fontName, (double)size);
        return font;
    }
    
    public static Font loadFontLocal(final String fullFont, final String fontName, final float size) {
        Font.loadFont(FontLoader.class.getResourceAsStream(String.valueOf("/resources/") + fullFont), 14.0);
        final Font font = Font.font(fontName, (double)size);
        return font;
    }
}