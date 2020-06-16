package savings;

import java.io.Serializable;

/**
 * I need this class for putting all objects in a single class for saving it in a single file.
 * @author Alessandro Becci
 *
 */
public class SaveResource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2546103703624570995L;
	private final Object object1;
	private final Object object2;
	private final Object object3;
	
	public SaveResource(final Object object1, final Object object2, final Object object3) {
		this.object1 = object1;
		this.object2 = object2;
		this.object3 = object3;
	}

	public Object getObject1() {
		return object1;
	}

	public Object getObject2() {
		return object2;
	}

	public Object getObject3() {
		return object3;
	}
	
}
