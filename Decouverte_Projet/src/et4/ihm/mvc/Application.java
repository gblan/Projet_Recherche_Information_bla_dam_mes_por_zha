package et4.ihm.mvc;


public class Application {

	public Application() {
		
	}
	
	public void start() {
		
		Model model = new Model();
		
		View view = new View("Chinois Facile",model);
		model.addObserver(view);
		
//		model.fillSearch(new ArrayList<SearchComponent>());
		model.fillKnowledge(null);
		
		Controller controller = new Controller(model, view);
		
		view.addController(controller);
		
		
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		app.start();
	}
	
}
