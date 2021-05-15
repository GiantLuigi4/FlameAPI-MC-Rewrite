package net.minecraft.vecmath;

import java.nio.FloatBuffer;

public class Matrix4x4 {
	public Matrix4x4() {
	}
	
	public Matrix4x4(Matrix4x4 parent) {
	}
	
	// TODO: quaternion constructor
	
	/**
	 * Method is only present client side
	 */
	public void toFloatBuffer(FloatBuffer buffer) {
	}
	
	/**
	 * Method is only present client side
	 */
	public void identity() {
	}
	
	/**
	 * Method is only present client side
	 */
	public void scale(float amplifier) {
	}
	
	/**
	 * Method is only present client side
	 */
	public void multiply(Matrix4x4 matrix) {
	}
	
	// TODO: rotate method
	
	/**
	 * Method is only present client side
	 */
	public static Matrix4x4 perspectiveMatrix(double fov, float aspect, float near, float far) {
		return new Matrix4x4();
	}
	
	/**
	 * Method is only present client side
	 */
	public static Matrix4x4 orthographicMatrix(float width, float height, float near, float far) {
		return new Matrix4x4();
	}
	
	public Matrix4x4 copy() {
		return new Matrix4x4();
	}
	
	public static Matrix4x4 translationMatrix(float x, float y, float z) {
		return new Matrix4x4();
	}
	
	public static Matrix4x4 scaleMatrix(float x, float y, float z) {
		return new Matrix4x4();
	}
}
