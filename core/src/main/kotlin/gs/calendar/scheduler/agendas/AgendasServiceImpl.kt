package gs.calendar.scheduler.agendas

import com.google.api.services.calendar.Calendar
import gs.calendar.scheduler.model.Agenda
import javax.inject.Inject

internal class AgendasServiceImpl @Inject constructor(
        api: Calendar) : AgendasService {

    private val listRequest = api.calendarList()
            .list()

    override fun list() = listRequest.execute()
            .let { it.items }
            .map { Agenda(id = it.id, name = it.summary, color = it.colorId) }

}
