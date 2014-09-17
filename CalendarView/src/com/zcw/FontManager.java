package com.zcw;

import java.util.WeakHashMap;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontManager {
	
	public enum Fonts{
//		DOPPIO_ONE_REGULAR("DoppioOne_Regular", "DoppioOne_Regular.ttf"),
//		DOSIS_REGULAR("Dosis_Regular", "Dosis_Regular.ttf"),
//		UBUNTU_REGULAR("Ubuntu_Regular", "Ubuntu_Regular.ttf"),
//		NUNITO_REGULAR("Nunito_Regular", "Nunito_Regular.ttf"),
//		SIGNIKA_REGULAR("Signika_Regular", "Signika_Regular.ttf"),
		BARIOL_REGULAR("Bariol_Regular.ttf"),
		;
		public String file;
		Fonts(String _file){
			file = _file;
		}
	}
	
	private static FontManager instance;
	private static WeakHashMap<Fonts, Typeface> typefaces = new WeakHashMap<Fonts, Typeface>();
	
	private FontManager(){
	}
	
	public static FontManager init(AssetManager asset){
		if(instance == null){
			instance = new FontManager();
			for (Fonts f : Fonts.values()) {
				Typeface font = Typeface.createFromAsset(asset, f.file);
				typefaces.put(f, font);
			}
			
		}
		return instance;
	}
	
	public static FontManager getInstance(){
		return instance;
	}
	
	public Typeface getTypeface(Fonts font){
		return typefaces.get(font);
	}
	
}
