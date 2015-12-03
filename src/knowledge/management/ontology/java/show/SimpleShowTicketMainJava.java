package knowledge.management.ontology.java.show;

import knowledge.management.ontology.groovy.initializer.OWLDefaultInitializer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by nik on 03.12.15.
 */
public class SimpleShowTicketMainJava {

  private final OWLDefaultInitializer initializer;

  /**
   * delegate initialization to extra class
   */
  public SimpleShowTicketMainJava() {
    this.initializer = new OWLDefaultInitializer();
  }

  public final OWLDefaultInitializer getInitializer() {
    return initializer;
  }

  public static void main(String... args) {
    final SimpleShowTicketMainJava app = new SimpleShowTicketMainJava();

    System.out.println("\nOntology loaded from: " + String.valueOf(app.getInitializer().getManager().getOntologyDocumentIRI(app.getInitializer().getOntology())));
    System.out.println("Print OWL-Classes with the power of closures!\n");

    //get class and its individuals
    final OWLClass ticket = app.getInitializer().getFactory().getOWLClass(":Ticket", app.getInitializer().getPm());
    app.printOWLClass(app, ticket);

    final OWLClass employee = app.getInitializer().getFactory().getOWLClass(":Mitarbeiter", app.getInitializer().getPm());
    app.printOWLClass(app, employee);
    /**
     * more to come
     */

    // get a given individual Ticket
    app.printDefaultInformation(app, ":happyTicket", ":hat", ":hatQualifikationFür", ":hatBearbeiteterZustand", ":hatRückgemeldetenZustand");
    app.printDefaultInformation(app, ":sadTicket", ":hat", ":hatBearbeiteterZustand", ":hatQualifikationFür", ":hatRückgemeldetenZustand");

    // get a given individual employee
    app.printDefaultInformation(app, ":Mitarbeiter3", ":bearbeitet", ":erstellt", ":gibtRückmeldungFür", ":hatBearbeiteterZustand", ":hatQualifikationFür", ":hatRückgemeldetenZustand");
    app.printDefaultInformation(app, ":MA_TicketQualifizierer1", ":bearbeitet", ":erstellt", ":gibtRückmeldungFür", ":hatBearbeiteterZustand", ":hatQualifikationFür", ":hatRückgemeldetenZustand");

  }

  public void printOWLClass(final SimpleShowTicketMainJava app, final OWLClass owlClass) {
    System.out.println(app.getInitializer().getRenderer().render(owlClass));
    app.getInitializer().getReasoner().getInstances(owlClass, false).getFlattened().forEach(it ->
        System.out.println("\t" + app.getInitializer().getRenderer().render(it))
    );
    System.out.println("\n");
  }

  public void printOWLObjectProperty(final SimpleShowTicketMainJava app, final OWLNamedIndividual individual, final OWLObjectProperty owlObjectProperty, final String objectPropertyIndividual) {
    System.out.println(app.getInitializer().getRenderer().render(individual) + " " + objectPropertyIndividual);
    app.getInitializer().getReasoner().getObjectPropertyValues(individual, owlObjectProperty).getFlattened().forEach(it ->
        System.out.println("\t" + app.getInitializer().getRenderer().render(it))
    );
  }

  public void printDefaultInformation(final SimpleShowTicketMainJava app, final String individualAsString, final String... objectProperties) {
    final OWLNamedIndividual individual = app.getInitializer().getFactory().getOWLNamedIndividual(individualAsString, app.getInitializer().getPm());

    //get values of selected property on the individual

    Arrays.asList(objectProperties).forEach(it -> {
      final OWLObjectProperty objProp = app.getInitializer().getFactory().getOWLObjectProperty(it, app.getInitializer().getPm());
      app.printOWLObjectProperty(app, individual, objProp, it);
    });
    System.out.println("\n");
    //find to which classes the individual belongs
    Set<OWLClassExpression> assertedClasses = individual.getTypes(app.getInitializer().getOntology());
    app.getInitializer().getReasoner().getTypes(individual, false).getFlattened().forEach(c -> {
      final boolean asserted = assertedClasses.contains(c);
      System.out.println("\n" + (asserted ? "asserted" : "inferred") + " class for happyTicket: " + app.getInitializer().getRenderer().render(c));
    });
    System.out.println("\n");
    //list all object property values for the individual

    app.getInitializer().getOntology().getObjectPropertiesInSignature(true).forEach(objProp ->
        app.getInitializer().getReasoner().getObjectPropertyValues(individual, objProp).getFlattened().forEach(ind ->
            System.out.println("Object property for " + individualAsString + " : " + app.getInitializer().getRenderer().render(objProp) + " -> " + app.getInitializer().getRenderer().render(ind))));

    System.out.println("\n\n");
  }

}
