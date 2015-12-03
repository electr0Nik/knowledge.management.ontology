package knowledge.management.ontology.groovy.crud

import knowledge.management.ontology.groovy.initializer.OWLDefaultInitializer
import org.semanticweb.owlapi.model.AddAxiom
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom
import org.semanticweb.owlapi.model.OWLDeclarationAxiom
import org.semanticweb.owlapi.model.OWLIndividual

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
    final OWLDeclarationAxiom declarationAxiomTickets = app.getInitializer().getFactory().getOWLDeclarationAxiom(ticket)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomTickets)

    final OWLClass employee = app.getInitializer().getFactory().getOWLClass(":Mitarbeiter", app.getInitializer().getPm())
    final OWLDeclarationAxiom declarationAxiomEmployee = app.getInitializer().getFactory().getOWLDeclarationAxiom(employee)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomEmployee)

    final OWLClass employeeQualify = app.getInitializer().getFactory().getOWLClass(":TicketQualifizierer", app.getInitializer().getPm())
    final OWLDeclarationAxiom declarationAxiomEmployeeQualify = app.getInitializer().getFactory().getOWLDeclarationAxiom(employeeQualify)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomEmployeeQualify)

    //add subclass relations to the new classes
    final OWLAxiom axiomEmployeeEmployee = app.getInitializer().getFactory().getOWLSubClassOfAxiom(employee, employeeQualify)
    final AddAxiom addAxiom1 = new AddAxiom(app.getInitializer().getOntology(), axiomEmployeeEmployee);
    app.getInitializer().getManager().applyChange(addAxiom1);

    final OWLAxiom axiomEmployeeTicket = app.getInitializer().getFactory().getOWLSubClassOfAxiom(employee, ticket)
    final AddAxiom addAxiom2 = new AddAxiom(app.getInitializer().getOntology(), axiomEmployeeTicket);
    app.getInitializer().getManager().applyChange(addAxiom2);

    //create individual for the malePerson class
    final OWLIndividual ticket3 = app.getInitializer().getFactory().getOWLNamedIndividual(":happySadTicket", app.getInitializer().getPm());
    final OWLDeclarationAxiom declarationAxiom4 = app.getInitializer().getFactory().getOWLDeclarationAxiom(ticket3);
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiom4);

    final OWLClassAssertionAxiom caAxiom = app.getInitializer().getFactory().getOWLClassAssertionAxiom(employee, ticket3);
    app.getInitializer().getManager().addAxiom(app.getInitializer().getFactory(), caAxiom);

    // Individual Ticket also should belong to class Father
    OWLClass father = app.getInitializer().getFactory().getOWLClass(":Mitarbeiter3", app.getInitializer().getPm());
    OWLClassAssertionAxiom caAxiom2 = app.getInitializer().getFactory().getOWLClassAssertionAxiom(father, ticket3);
    /*
    manager.addAxiom(app.getInitializer().getOntology(), caAxiom2);
     * /

    //create a property
    OWLObjectProperty isA = factory.getOWLObjectProperty(":isA", pm);
    OWLDeclarationAxiom declarationAxiom5 = factory.getOWLDeclarationAxiom(isA);
    manager.addAxiom(ontology, declarationAxiom5);

    //define domain and range for the property isA
    OWLObjectPropertyDomainAxiom opd1 = factory.getOWLObjectPropertyDomainAxiom(isA, father);
    OWLObjectPropertyRangeAxiom opd2 = factory.getOWLObjectPropertyRangeAxiom(isA, malePerson);
    manager.addAxiom(ontology, opd1);
    manager.addAxiom(ontology, opd2);

*/






    final IRI documentIRI = IRI.create(new File("res/ticketOntology_new_v1.owl"))
    app.getInitializer().getManager().saveOntology(app.getInitializer().getOntology(), documentIRI)
    print "\nExtended Ontology stored in ${documentIRI}"
  }
}
