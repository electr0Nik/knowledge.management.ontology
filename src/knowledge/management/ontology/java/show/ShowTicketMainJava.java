package knowledge.management.ontology.java.show;

import knowledge.management.ontology.groovy.initializer.OWLDefaultInitializer;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.NodeSet;


public class ShowTicketMainJava {

  private final OWLDefaultInitializer initializer;

  /**
   * delegate initialization to extra class
   */
  public ShowTicketMainJava() {
    this.initializer = new OWLDefaultInitializer();
  }

  public final OWLDefaultInitializer getInitializer() {
    return initializer;
  }

  /**
   * <h1>steps</h1>
   * <p>
   * <ul>
   * <li>get default OWL-Class map and iterate over each entry</li>
   * <li>iterate over each class of map value (owl_class for ticket or employee) and get corresponding individuals</li>
   * <li>iterate over each objectProperty</li>
   * <li>get object property by represented string value of implicated object property it</li>
   * </ul>
   * </p>
   *
   * @param args
   */
  public static void main(String... args) {
    final ShowTicketMainJava app = new ShowTicketMainJava();

    System.out.println("\nOntology loaded from: " + app.getInitializer().getManager().getOntologyDocumentIRI(app.getInitializer().getOntology()));
    System.out.println("Print OWL-Classes!\n");

    app.getInitializer().getInitializedDefaultMap().forEach((mapKey, mapValue) -> {
      System.out.println("\nOWLClass: " + mapKey);
      app.getInitializer().getReasoner().getInstances(mapValue, false).getFlattened().forEach(ind -> {
            final OWLNamedIndividual owlNamedIndividual = ind;
            final String namedIndividual = app.getInitializer().getRenderer().render(owlNamedIndividual);
            System.out.println("\n\tOWLNamedIndividual: " + namedIndividual);

            /**
             * prints the ticket information
             */
            app.printOWLDataProperty(app, owlNamedIndividual, "");

            /**
             * prints the property information
             */
            app.printOWLObjectProperty(app, owlNamedIndividual);
          }
      );
    });
  }

  public void printOWLDataProperty(final ShowTicketMainJava app, final OWLNamedIndividual owlNamedIndividual, final String tabs) {
    app.getInitializer().getOntology().getDataPropertiesInSignature(true).forEach(it ->
        app.getInitializer().getReasoner().getDataPropertyValues(owlNamedIndividual, it).forEach(ind ->
            System.out.println(tabs + "\t\tOWLDataProperty: " + app.getInitializer().getRenderer().render(it) + " -> " + app.getInitializer().getRenderer().render(ind))
        )
    );
  }

  public void printOWLObjectProperty(final ShowTicketMainJava app, final OWLNamedIndividual owlNamedIndividual) {
    app.getInitializer().getOntology().getObjectPropertiesInSignature(true).forEach(it -> {
      final String objectPropertyStringValue = ":" + app.getInitializer().getRenderer().render(it);
      final NodeSet<OWLNamedIndividual> nodeSet = app.getInitializer().getReasoner().getObjectPropertyValues(owlNamedIndividual, it);
      if (!nodeSet.isEmpty()) {
        System.out.println("\t\tOWLObjectProperty: " + objectPropertyStringValue);
        app.getInitializer().getReasoner().getObjectPropertyValues(owlNamedIndividual, it).getFlattened().forEach(ind -> {
              final OWLNamedIndividual owlObjectPropertyNamedIndividual = ind;
              System.out.println("\t\t\tOWLNamedIndividual: " + app.getInitializer().getRenderer().render(owlObjectPropertyNamedIndividual));
              app.printOWLDataProperty(app, owlObjectPropertyNamedIndividual, "\t\t");
              //app.printOWLDataProperty(app, owlNamedIndividual)
            }
        );
      }
    });
  }

}
