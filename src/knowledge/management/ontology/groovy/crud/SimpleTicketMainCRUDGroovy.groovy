package knowledge.management.ontology.groovy.crud

import knowledge.management.ontology.groovy.initializer.OWLDefaultInitializer
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLDeclarationAxiom

/**
 * Created by nik on 03.12.15.
 */
class SimpleTicketMainCRUDGroovy {

  final OWLDefaultInitializer initializer

  /**
   * delegate initialization to extra class
   */
  SimpleTicketMainCRUDGroovy() {
    this.initializer = new OWLDefaultInitializer()
  }

  static void main(String... args) {
    final SimpleTicketMainCRUDGroovy app = new SimpleTicketMainCRUDGroovy()

    final OWLClass ticket = app.getInitializer().getFactory().getOWLClass(":Ticket", app.getInitializer().getPm())
    final OWLClass employee = app.getInitializer().getFactory().getOWLClass(":Mitarbeiter", app.getInitializer().getPm())

    final OWLDeclarationAxiom declarationAxiomTickets = app.getInitializer().getFactory().getOWLDeclarationAxiom(ticket)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomTickets)

    final OWLDeclarationAxiom declarationAxiomEmployee = app.getInitializer().getFactory().getOWLDeclarationAxiom(employee)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomEmployee)


    final IRI documentIRI = IRI.create(new File("res/ticketOntology_new_v1.owl"))
    app.getInitializer().getManager().saveOntology(app.getInitializer().getOntology(), documentIRI)
    print "\nExtended Ontology stored in ${documentIRI}"
  }
}
