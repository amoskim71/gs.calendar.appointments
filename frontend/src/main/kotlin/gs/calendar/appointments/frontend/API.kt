package gs.calendar.appointments.frontend

import axios.Axios
import gs.calendar.appointments.frontend.BuildConfig.API_ENDPOINT
import gs.calendar.appointments.model.Agenda
import gs.calendar.appointments.model.AgendaId
import gs.calendar.appointments.model.Slot
import gs.calendar.appointments.model.SlotId
import gs.calendar.appointments.model.User
import sanitized

object API {

    fun listAgendas() = Axios
        .get<Array<Agenda>>("$API_ENDPOINT/agendas")
        .then { it.data!! }

    fun listSlots(agendaId: AgendaId) = Axios
        .get<Array<Slot>>("$API_ENDPOINT/slots/$agendaId")
        .then { it.data!!.forEach { s -> s.sanitize() }; it.data!! }

    fun book(agendaId: AgendaId, slotId: SlotId, user: User) = Axios
        .put<Slot>("$API_ENDPOINT/slots/$agendaId/$slotId", user)
        .then { it.data!!.sanitize() }

    /**
     * Required because the JSON is being "casted" directly to the given types, without any deserialization process
     */
    private fun Slot.sanitize() = apply {
        with(asDynamic()) {
            this.startTime = startTime?.sanitized
            this.endTime = endTime?.sanitized
            this.attendees = attendees.sanitized
        }
    }

}
