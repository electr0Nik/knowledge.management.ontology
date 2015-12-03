package knowledge.management.ontology.groovy.common

/**
 * Created by nik on 02.12.15.
 */
class ApplicationConstants {

  static final String BASE_URL = "http://www.semanticweb.org/peter/ontologies/2015/10/untitled-ontology-13"
  static final String FILE_LOCATION_V1 = "res/ticketOntology.owl"
  static final String FILE_LOCATION_V2 = "res/ticketOntology_v2.owl"
  static final String FILE_LOCATION_CREATED = "res/ticketOntology_new_v1.owl"

  // OWL-Classes
  static final String OWL_CLASS_EMPLOYEE = "Mitarbeiter"

  static final String OWL_CLASS_PRIORITY = "Priorität"
  static final String OWL_CLASS_PRIORITY_HIGH = "hoch"
  static final String OWL_CLASS_PRIORITY_LOW = "nieder"
  static final String OWL_CLASS_PRIORITY_NORMAL = "normal"

  static final String OWL_CLASS_QUALIFIER = "TicketQualifizierer"

  static final String OWL_CLASS_RESPONSE = "Rückmeldung"

  static final String OWL_CLASS_STATE = "Status"
  static final String OWL_CLASS_STATE_CLOSED = "geschlossen"
  static final String OWL_CLASS_STATE_DUPLICATE = "duplikat"
  static final String OWL_CLASS_STATE_DONE = "erledigt"
  static final String OWL_CLASS_STATE_IN_PROGRESS = "inArbeit"
  static final String OWL_CLASS_STATE_OPENED = "geöffnet"
  static final String OWL_CLASS_STATE_QUALIFIED = "qualifiziert"
  static final String OWL_CLASS_STATE_RESOLVED = "gelöst"
  static final String OWL_CLASS_STATE_WONT_FIX = "wirdNichtGelöst"
  static final String OWL_CLASS_STATE_WORKING = "inBearbeitung"

  static final String OWL_CLASS_TASK_STATE = "AufgabenStatus"
  static final String OWL_CLASS_TASK_UNRESOLVED = "unzureichendBearbeitet"
  static final String OWL_CLASS_TASK_RESOLVED = "zufriedenstellendBearbeitet"

  static final String OWL_CLASS_TICKET = "Ticket"
}


