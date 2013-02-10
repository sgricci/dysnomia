package com.dysnomia.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GLLoader {
	public static void initGL(DisplayMode displayMode) {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClearDepth(1.0f);
    	glEnable(GL_DEPTH_TEST);
    	glEnable(GL_COLOR_MATERIAL);
    	glDepthFunc(GL_LEQUAL);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, (float) displayMode.getWidth() / (float) displayMode.getHeight(), 0.1f, 100.0f);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		//glEnable(GL_BLEND);
		//glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		// Lighting
		
		FloatBuffer matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(4.0f).put(0.0f).put(8.0f).put(1.0f).flip();
		
		FloatBuffer lightColor = BufferUtils.createFloatBuffer(4);
		lightColor.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
		
		FloatBuffer lModelAmbient = BufferUtils.createFloatBuffer(4);
        lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
		
        glEnable(GL_FLAT);
		glShadeModel(GL_FLAT);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

		
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
		glLight(GL_LIGHT0, GL_DIFFUSE, lightColor);
		
		FloatBuffer lightColor2 = BufferUtils.createFloatBuffer(4);
		lightColor2.put(0.2f).put(0.2f).put(0.5f).put(1.0f).flip();
		
		FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
		lightPos2.put(-1.0f).put(0.5f).put(0.5f).put(0.0f).flip();
		
		glLight(GL_LIGHT1, GL_DIFFUSE, lightColor2);
		glLight(GL_LIGHT1, GL_POSITION, lightPos2);
		
		glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);
		
		enableLighting();
		
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		// End Lighting
	}

	public static void enterOrtho() {
		glMatrixMode(GL_PROJECTION);
    	glPushMatrix();
    	glLoadIdentity();
    	glOrtho(0,Display.getWidth(),Display.getHeight(),0, 1, -1);
    	//glViewport(0, Display.getWidth(), Display.getHeight(), 0);
    	//GLU.gluOrtho2D(0,Display.getWidth(), Display.getHeight(), 0);
    	
    	glMatrixMode(GL_MODELVIEW);
    	glPushMatrix();
    	
    	glLoadIdentity();
    	
    	glDisable(GL_DEPTH_TEST);
    	disableLighting();
	}

	public static void disableLighting() {
		glDisable(GL_LIGHTING);
		glDisable(GL_LIGHT0);
		glDisable(GL_LIGHT1);
		glDisable(GL_COLOR_MATERIAL);
		glDisable(GL_NORMALIZE);
	}

	public static void exitOrtho() {
		glEnable(GL_DEPTH_TEST);
    	glMatrixMode(GL_PROJECTION);
    	glPopMatrix();
    	glMatrixMode(GL_MODELVIEW);
    	glPopMatrix();
    	enableLighting();
	}

	public static void enableLighting() {
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_LIGHT1);
		glEnable(GL_COLOR_MATERIAL);
		glEnable(GL_NORMALIZE);
	}
}
