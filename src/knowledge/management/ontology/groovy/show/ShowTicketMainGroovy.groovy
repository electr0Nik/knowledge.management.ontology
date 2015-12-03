package knowledge.management.ontology.groovy.show

import knowledge.management.ontology.groovy.initializer.OWLDefaultInitializer
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.reasoner.NodeSet

class ShowTicketMainGroovy {

  final OWLDefaultInitializer initializer

  /**
   * delegate initialization to extra class
   */
  ShowTicketMainGroovy() {
    this.initializer = new OWLDefaultInitializer()
  }

  /**
   *
   * <h1>steps</h1>
   * <p>
   * <ul>
   *   <li>get default OWL-Class map and iterate over each entry</li>
   *   <li>iterate over each class of map value (owl_class for ticket or employee) and get corresponding individuals</li>
   *   <li>iterate over each objectProperty</li>
   *   <li>get object property by represented string value of implicated object property it</li>
   * </ul>
   * </p>
   *
   * @param args
   */
  static void main(String... args) {
    final ShowTicketMainGroovy app = new ShowTicketMainGroovy()

    println("\nOntology loaded from: ${app.getInitializer().getManager().getOntologyDocumentIRI(app.getInitializer().getOntology())}")
    println("Print OWL-Classes with the power of closures!\n")

    app.getInitializer().getInitializedDefaultMap().each {
      final String key = it.key
      final OWLClass owlClass = it.value
      println("\nOWLClass: ${key}")

      app.getInitializer().getReasoner().getInstances(owlClass, false).getFlattened().each {
        final OWLNamedIndividual owlNamedIndividual = it
        final String namedIndividual = app.getInitializer().getRenderer().render(owlNamedIndividual)
        println("\n\tOWLNamedIndividual: ${namedIndividual}")

        /**
         * prints the ticket information
         */
        app.printOWLDataProperty(app, owlNamedIndividual)

        /**
         * prints the property information
         */
        app.printOWLObjectProperty(app, owlNamedIndividual)
      }
    }
  }

  void printOWLDataProperty(final ShowTicketMainGroovy app, final OWLNamedIndividual owlNamedIndividual) {
    app.getInitializer().getOntology().getDataPropertiesInSignature(true).each {
      final OWLDataProperty owlDataProperty = it
      for (OWLLiteral ind : app.getInitializer().getReasoner().getDataPropertyValues(owlNamedIndividual, owlDataProperty))
        println("\t\tOWLDataProperty: ${app.getInitializer().getRenderer().render(owlDataProperty)} -> ${app.getInitializer().getRenderer().render(ind)}")
    }
  }

  void printOWLObjectProperty(final ShowTicketMainGroovy app, final OWLNamedIndividual owlNamedIndividual) {
    app.getInitializer().getOntology().getObjectPropertiesInSignature(true).each {
      final OWLObjectProperty owlObjectProperty = it
      final String objectPropertyStringValue = ":${app.getInitializer().getRenderer().render(owlObjectProperty)}"
      final NodeSet<OWLNamedIndividual> nodeSet = app.getInitializer().getReasoner().getObjectPropertyValues(owlNamedIndividual, owlObjectProperty)
      if (!nodeSet.isEmpty()) {
        println("\t\tOWLObjectProperty: ${objectPropertyStringValue}")

        app.getInitializer().getReasoner().getObjectPropertyValues(owlNamedIndividual, owlObjectProperty).getFlattened().each {
          final OWLNamedIndividual owlObjectPropertyNamedIndividual = it
          println("\t\t\tOWLNamedIndividual: ${app.getInitializer().getRenderer().render(owlObjectPropertyNamedIndividual)}")
        }
      }
    }
  }
}
