package engine.components;

import org.joml.Vector3f;

public class PointLight extends Component{
	private Vector3f color;
	protected float intensity;

	private Attenuation attenuation;

	public PointLight(Vector3f color, float intensity) {
		setTag("PointLight");
		attenuation = new Attenuation(1, 0, 0);
		this.color = color;
		this.intensity = intensity;
	}
	public void start() {
		
	}
	public PointLight(Vector3f color, float intensity, Attenuation attenuation) {
		this(color, intensity);
		this.attenuation = attenuation;
	}

	public PointLight(PointLight pointLight) {
		this(new Vector3f(pointLight.getColor()), pointLight.getIntensity(),
				pointLight.getAttenuation());
	}
    public void update(float deltaTime) {
    	
    }

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}


	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public Attenuation getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(Attenuation attenuation) {
		this.attenuation = attenuation;
	}

	public static class Attenuation {

		private float constant;

		private float linear;

		private float exponent;

		public Attenuation(float constant, float linear, float exponent) {
			this.constant = constant;
			this.linear = linear;
			this.exponent = exponent;
		}

		public float getConstant() {
			return constant;
		}

		public void setConstant(float constant) {
			this.constant = constant;
		}

		public float getLinear() {
			return linear;
		}

		public void setLinear(float linear) {
			this.linear = linear;
		}

		public float getExponent() {
			return exponent;
		}

		public void setExponent(float exponent) {
			this.exponent = exponent;
		}
	}
}
