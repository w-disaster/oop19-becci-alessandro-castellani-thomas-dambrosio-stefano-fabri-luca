package savings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

	public List<Object> getResource() {
		List<Object> resources = new ArrayList<>();
		resources.add(0, object1);
		resources.add(1, object2);
		resources.add(2, object3);
		return resources;
	}

	
}
