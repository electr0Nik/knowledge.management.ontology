package knowledge.management.ontology.groovy.initializer

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory
import knowledge.management.ontology.groovy.common.ApplicationConstants
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLObjectRenderer
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.reasoner.OWLReasoner
import org.semanticweb.owlapi.reasoner.SimpleConfiguration
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer

class OWLDefaultInitializer {

  final OWLOntologyManager manager
  final OWLOntology ontology
  final OWLDataFactory factory
  final PrefixOWLOntologyFormat pm

  final OWLReasoner reasoner
  final OWLObjectRenderer renderer

  //prepare ontology
  OWLDefaultInitializer() {
    manager = OWLManager.createOWLOntologyManager()
    //ontology = manager.loadOntologyFromOntologyDocument(new File(ApplicationConstants.FILE_LOCATION_V1))
    ontology = manager.loadOntologyFromOntologyDocument(new File(ApplicationConstants.FILE_LOCATION_V2))
    //ontology = manager.loadOntologyFromOntologyDocument(new File(ApplicationConstants.FILE_LOCATION_CREATED))
    factory = manager.getOWLDataFactory()
    pm = (PrefixOWLOntologyFormat) manager.getOntologyFormat(ontology)
    pm.setDefaultPrefix(ApplicationConstants.BASE_URL + "#")

    reasoner = PelletReasonerFactory.getInstance().createReasoner(ontology, new SimpleConfiguration())
    renderer = new DLSyntaxObjectRenderer()
  }

  /**
   * get owl class for name
   *
   * @return
   */
  Map<String, OWLClass> getInitializedDefaultMap() {
    final Map<String, OWLClass> classMap = new HashMap<>()
    classMap.put(ApplicationConstants.OWL_CLASS_EMPLOYEE, this.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_EMPLOYEE}", this.getPm()))
//    classMap.put(ApplicationConstants.OWL_CLASS_PRIORITY, this.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_PRIORITY}", this.getPm()))
//    classMap.put(ApplicationConstants.OWL_CLASS_QUALIFIER, this.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_QUALIFIER}", this.getPm()))
//    classMap.put(ApplicationConstants.OWL_CLASS_STATE, this.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_STATE}", this.getPm()))
//    classMap.put(ApplicationConstants.OWL_CLASS_TASK_STATE, this.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_TASK_STATE}", this.getPm()))
    classMap.put(ApplicationConstants.OWL_CLASS_TICKET, this.getFactory().getOWLClass(":${ApplicationConstants.OWL_CLASS_TICKET}", this.getPm()))
    return classMap;
  }
}
