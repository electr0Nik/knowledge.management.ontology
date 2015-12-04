package knowledge.management.ontology.groovy.crud

import knowledge.management.ontology.groovy.common.ApplicationConstants
import knowledge.management.ontology.groovy.initializer.OWLDefaultInitializer
import org.semanticweb.owlapi.model.*

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

    // create classes

    final OWLClass ticket = app.getInitializer().getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_TICKET}", app.getInitializer().getPm())
    final OWLClass employee = app.getInitializer().getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_EMPLOYEE}", app.getInitializer().getPm())
    final OWLClass qualifier = app.getInitializer().getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_QUALIFIER}", app.getInitializer().getPm())
    final OWLClass response = app.getInitializer().getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_RESPONSE}", app.getInitializer().getPm())
    final OWLClass priority = app.getInitializer().getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_PRIORITY}", app.getInitializer().getPm())
    final OWLClass state = app.getInitializer().getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE}", app.getInitializer().getPm())
    final OWLClass taskState = app.getInitializer().getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_TASK_STATE}", app.getInitializer().getPm())

    final OWLDeclarationAxiom declarationAxiomTickets = app.getInitializer().getFactory().getOWLDeclarationAxiom(ticket)
    final OWLDeclarationAxiom declarationAxiomEmployee = app.getInitializer().getFactory().getOWLDeclarationAxiom(employee)
    final OWLDeclarationAxiom declarationAxiomQualifier = app.getInitializer().getFactory().getOWLDeclarationAxiom(qualifier)
    final OWLDeclarationAxiom declarationAxiomResponse = app.getInitializer().getFactory().getOWLDeclarationAxiom(response)
    final OWLDeclarationAxiom declarationAxiomPriority = app.getInitializer().getFactory().getOWLDeclarationAxiom(priority)
    final OWLDeclarationAxiom declarationAxiomState = app.getInitializer().getFactory().getOWLDeclarationAxiom(state)
    final OWLDeclarationAxiom declarationAxiomTaskState = app.getInitializer().getFactory().getOWLDeclarationAxiom(taskState)

    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomTickets)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomEmployee)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomQualifier)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomResponse)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomPriority)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomState)
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomTaskState)

    /**
     * add subclass relations to the new classes
     */
    // employee -> ticket ; ticket -> employee
    final OWLAxiom axiomEmployeeToTicket = app.getInitializer().getFactory().getOWLSubClassOfAxiom(employee, ticket)
    final OWLAxiom axiomTicketToEmployee = app.getInitializer().getFactory().getOWLSubClassOfAxiom(ticket, employee)
    // employee -> response
    final OWLAxiom axiomEmployeeToResponse = app.getInitializer().getFactory().getOWLSubClassOfAxiom(response, employee)
    // ticket -> priority
    final OWLAxiom axiomTicketToPriority = app.getInitializer().getFactory().getOWLSubClassOfAxiom(priority, ticket)
    // ticket -> state
    final OWLAxiom axiomTicketToState = app.getInitializer().getFactory().getOWLSubClassOfAxiom(state, ticket)
    // ticket -> qualifier ; qualifier -> ticket
    final OWLAxiom axiomTicketToQualifier = app.getInitializer().getFactory().getOWLSubClassOfAxiom(ticket, qualifier)
    final OWLAxiom axiomQualifierToTicket = app.getInitializer().getFactory().getOWLSubClassOfAxiom(qualifier, ticket)
    // ticket -> task_state
    final OWLAxiom axiomTicketToTaskState = app.getInitializer().getFactory().getOWLSubClassOfAxiom(taskState, ticket)
    // qualifier -> employee
    final OWLAxiom axiomQualifierToEmployee = app.getInitializer().getFactory().getOWLSubClassOfAxiom(employee, qualifier)


    final List<AddAxiom> axiomList = new ArrayList<>()
    axiomList.add(new AddAxiom(app.getInitializer().getOntology(), axiomEmployeeToTicket))
    axiomList.add(new AddAxiom(app.getInitializer().getOntology(), axiomEmployeeToResponse))
    axiomList.add(new AddAxiom(app.getInitializer().getOntology(), axiomTicketToPriority))
    axiomList.add(new AddAxiom(app.getInitializer().getOntology(), axiomTicketToState))
    axiomList.add(new AddAxiom(app.getInitializer().getOntology(), axiomTicketToQualifier))
    axiomList.add(new AddAxiom(app.getInitializer().getOntology(), axiomTicketToTaskState))
    axiomList.add(new AddAxiom(app.getInitializer().getOntology(), axiomTicketToEmployee))
    axiomList.add(new AddAxiom(app.getInitializer().getOntology(), axiomQualifierToEmployee))
    axiomList.add(new AddAxiom(app.getInitializer().getOntology(), axiomQualifierToTicket))

    //axiomList.each { app.getInitializer().getManager().applyChange(it) }


    / test /

    //create individual for the malePerson class
    final OWLIndividual owlIndividual = app.getInitializer().getFactory().getOWLNamedIndividual(":someTicket", app.getInitializer().getPm());

    final OWLDeclarationAxiom declarationAxiomForIndividual = app.getInitializer().getFactory().getOWLDeclarationAxiom(owlIndividual);
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomForIndividual);

    final OWLClassAssertionAxiom classAssertionAxiom = app.getInitializer().getFactory().getOWLClassAssertionAxiom(ticket, owlIndividual);
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), classAssertionAxiom);

    //create a property
    final OWLObjectProperty has = app.getInitializer().getFactory().getOWLObjectProperty(":hat", app.getInitializer().getPm());
    final OWLDeclarationAxiom declarationAxiomForHas = app.getInitializer().getFactory().getOWLDeclarationAxiom(has);
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), declarationAxiomForHas);

    //define domain and range for the property has
    final OWLObjectPropertyDomainAxiom opd1 = app.getInitializer().getFactory().getOWLObjectPropertyDomainAxiom(has, qualifier);
    app.getInitializer().getManager().addAxiom(app.getInitializer().getOntology(), opd1);

    / end /


    final IRI documentIRI = IRI.create(new File("res/ticketOntology_v2_new_v1.owl"))
    app.getInitializer().getManager().saveOntology(app.getInitializer().getOntology(), documentIRI)
    print "\nExtended Ontology stored in ${documentIRI}"
  }
}
