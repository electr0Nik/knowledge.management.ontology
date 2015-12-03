package knowledge.management.ontology.groovy.show

import knowledge.management.ontology.groovy.initializer.OWLDefaultInitializer
import org.semanticweb.owlapi.model.*

/**
 * Created by nik on 03.12.15.
 */
class SimpleShowTicketMainGroovy {

  final OWLDefaultInitializer initializer

  /**
   * delegate initialization to extra class
   */
  SimpleShowTicketMainGroovy() {
    this.initializer = new OWLDefaultInitializer()
  }

  /**
   * user closures because they are fancy
   * @param args
   */
  static void main(String... args) {
    final SimpleShowTicketMainGroovy app = new SimpleShowTicketMainGroovy()

    println("\nOntology loaded from: ${app.getInitializer().getManager().getOntologyDocumentIRI(app.getInitializer().getOntology())}")
    println("Print OWL-Classes with the power of closures!\n")

    //get class and its individuals
    final OWLClass ticket = app.getInitializer().getFactory().getOWLClass(":Ticket", app.getInitializer().getPm())
    app.printOWLClass(app, ticket)

    final OWLClass employee = app.getInitializer().getFactory().getOWLClass(":Mitarbeiter", app.getInitializer().getPm())
    app.printOWLClass(app, employee)
    /**
     * more to come
     */

    // get a given individual Ticket
    app.printDefaultInformation(app, ":happyTicket", ":hat", ":hatQualifikationFür", ":hatBearbeiteterZustand", ":hatRückgemeldetenZustand")

    app.printDefaultInformation(app, ":sadTicket", ":hat", ":hatBearbeiteterZustand", ":hatQualifikationFür", ":hatRückgemeldetenZustand")

    // get a given individual employee
    app.printDefaultInformation(app, ":Mitarbeiter2", ":bearbeitet", ":erstellt", ":gibtRückmeldungFür",
        ":hatBearbeiteterZustand", ":hatQualifikationFür", ":hatRückgemeldetenZustand")

  }

  void printOWLClass(final SimpleShowTicketMainGroovy app, final OWLClass owlClass) {
    println app.getInitializer().getRenderer().render(owlClass)
    app.getInitializer().getReasoner().getInstances(owlClass, false).getFlattened().each {
      println("\t${app.getInitializer().getRenderer().render(it)}")
    }
    println "\n"
  }

  void printOWLObjectProperty(final SimpleShowTicketMainGroovy app, final OWLNamedIndividual individual, final OWLObjectProperty owlObjectProperty,
                              final String objectPropertyIndividual) {
    println "${app.getInitializer().getRenderer().render(individual)} ${objectPropertyIndividual}"
    app.getInitializer().getReasoner().getObjectPropertyValues(individual, owlObjectProperty).getFlattened().each {
      println("\t${app.getInitializer().getRenderer().render(it)}")
    }
    println "\n"
  }

  void printDefaultInformation(final SimpleShowTicketMainGroovy app, final String individualAsString, final String... objectProperties) {
    final OWLNamedIndividual individual = app.getInitializer().getFactory().getOWLNamedIndividual(individualAsString, app.getInitializer().getPm());

    //get values of selected property on the individual
    objectProperties.each {
      final OWLObjectProperty objProp = app.getInitializer().getFactory().getOWLObjectProperty(it, app.getInitializer().getPm());
      app.printOWLObjectProperty(app, individual, objProp, it)
    }

    //find to which classes the individual belongs
    Set<OWLClassExpression> assertedClasses = individual.getTypes(app.getInitializer().getOntology());
    for (OWLClass c : app.getInitializer().getReasoner().getTypes(individual, false).getFlattened()) {
      boolean asserted = assertedClasses.contains(c);
      System.out.println((asserted ? "asserted" : "inferred") + " class for happyTicket: " + app.getInitializer().getRenderer().render(c));
    }
    //list all object property values for the individual
    app.getInitializer().getOntology().getObjectPropertiesInSignature(true).each {
      final OWLObjectProperty objProp = it
      app.getInitializer().getReasoner().getObjectPropertyValues(individual, objProp).getFlattened().each {
        final OWLNamedIndividual ind = it
        println "Object property for ${individualAsString} : ${app.getInitializer().getRenderer().render(objProp)} -> ${app.getInitializer().getRenderer().render(ind)}"
      }
    }
  }
}
