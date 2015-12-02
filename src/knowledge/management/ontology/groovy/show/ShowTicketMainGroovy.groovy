package knowledge.management.ontology.groovy.show

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory
import knowledge.management.ontology.groovy.common.ApplicationConstants
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLObjectRenderer
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.reasoner.OWLReasoner
import org.semanticweb.owlapi.reasoner.SimpleConfiguration
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer

class ShowTicketMainGroovy {

  final OWLOntologyManager manager
  final OWLOntology ontology
  final OWLDataFactory factory
  final PrefixOWLOntologyFormat pm

  final OWLReasoner reasoner
  final IRI documentIRI
  final OWLObjectRenderer renderer

  //prepare ontology
  ShowTicketMainGroovy() {
    manager = OWLManager.createOWLOntologyManager()
    ontology = manager.loadOntologyFromOntologyDocument(new File(ApplicationConstants.FILE_LOCATION))
    factory = manager.getOWLDataFactory()
    pm = (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology)
    pm.setDefaultPrefix(ApplicationConstants.BASE_URL + "#")

    reasoner = PelletReasonerFactory.getInstance().createReasoner(ontology, new SimpleConfiguration())
    documentIRI = manager.getOntologyDocumentIRI(ontology)
    renderer = new DLSyntaxObjectRenderer()
  }


  static void main(String... args) {
    final ShowTicketMainGroovy app = new ShowTicketMainGroovy()

    println("Ontology loaded from: ${app.getDocumentIRI()}\n")
    println("Print OWL-Classes with the power of closures! : \n")

    //get class and its individuals
    final Map<String, OWLClass> classMap = new HashMap<>()
    classMap.put(ApplicationConstants.OWL_CLASS_EMPLOYEE, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_EMPLOYEE}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_PRIORITY, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_PRIORITY}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_PRIORITY_HIGH, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_PRIORITY_HIGH}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_PRIORITY_LOW, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_PRIORITY_LOW}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_PRIORITY_NORMAL, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_PRIORITY_NORMAL}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_QUALIFIER, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_QUALIFIER}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE_CLOSED, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE_CLOSED}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE_DUPLICATE, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE_DUPLICATE}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE_DONE, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE_DONE}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE_IN_PROGRESS, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE_IN_PROGRESS}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE_OPENED, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE_OPENED}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE_QUALIFIED, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE_QUALIFIED}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE_RESOLVED, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE_RESOLVED}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE_WONT_FIX, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE_WONT_FIX}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_STATE_WORKING, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE_WORKING}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_TASK_STATE, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_TASK_STATE}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_TASK_UNRESOLVED, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_TASK_UNRESOLVED}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_TASK_RESOLVED, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_TASK_RESOLVED}", app.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_TICKET, app.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_TICKET}", app.getPm()))

    classMap.each {
      final OWLClass owlClass = it.value
      println("\nClass: ${it.key}")
      app.getReasoner().getInstances(owlClass, false).getFlattened().each {
        println("Individual: ${app.getRenderer().render(it)} ")
        final OWLIndividual individual = it
        final OWLObjectProperty property = app.getFactory().getOWLObjectProperty(":hatQualifikationFür", app.getPm())
      }
    }

    //get a given individual
    final OWLNamedIndividual ticket1 = app.getFactory().getOWLNamedIndividual(":sadTicket", app.getPm())
    println("\n\n\n ticket: ${ticket1}")

    //get values of selected property on the individual
    final OWLObjectProperty hasChildProperty = app.getFactory().getOWLObjectProperty(":hatQualifikationFür", app.getPm());

    println("\n\n\n\n")
    for (OWLNamedIndividual ind : app.getReasoner().getObjectPropertyValues(ticket1, hasChildProperty).getFlattened()) {
      System.out.println("sadTicket has hasChildProperty: " + app.getRenderer().render(ind));
    }

    //get inverse of a property, i.e. which individuals are in relation with a given individual
    OWLNamedIndividual clara = app.getFactory().getOWLNamedIndividual(":happyTicket", app.getPm());
    OWLObjectPropertyExpression inverse = app.getFactory().getOWLObjectInverseOf(hasChildProperty);
    for (OWLNamedIndividual ind : app.getReasoner().getObjectPropertyValues(clara, inverse).getFlattened()) {
      System.out.println("happyTicket inverseOf(hasChild) -> " + app.getRenderer().render(ind));
    }

    //find to which classes the individual belongs
    Set<OWLClassExpression> assertedClasses = ticket1.getTypes(app.getOntology());
    for (OWLClass c : app.getReasoner().getTypes(ticket1, false).getFlattened()) {
      boolean asserted = assertedClasses.contains(c);
      System.out.println((asserted ? "asserted" : "inferred") + " class for sadTicket: " + app.getRenderer().render(c));
    }

    //list all object property values for the individual
    for (OWLObjectProperty objProp : app.getOntology().getObjectPropertiesInSignature(true)) {
      for (OWLNamedIndividual ind : app.getReasoner().getObjectPropertyValues(ticket1, objProp).getFlattened())
        System.out.println("Object property for sadTicket: "
            + app.getRenderer().render(objProp) + " -> " + app.getRenderer().render(ind));
    }

  }

}
