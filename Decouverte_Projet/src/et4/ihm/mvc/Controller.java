package et4.ihm.mvc;

/**
 * Class permettant de gerer les events sur l'interface
 * @author All
 *
 */
public class Controller {
	
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public Model getModel() {
		return model;
	}
	public View getView() {
		return view;
	}
	

	
}
