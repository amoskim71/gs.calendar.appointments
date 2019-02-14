package gs.calendar.appointments

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.calendar.Calendar
import dagger.Module
import dagger.Provides
import dagger.Reusable
import gs.calendar.appointments.agendas.AgendasModule
import gs.calendar.appointments.booking.BookingModule
import javax.inject.Named


@Module(includes = [AgendasModule::class, BookingModule::class])
internal class CoreModule {

    @Provides
    @Reusable
    fun provideCalendar(transport: HttpTransport,
                        jsonFactory: JsonFactory,
                        credential: Credential?,
                        @Named("applicationName") applicationName: String) =
            Calendar.Builder(transport, jsonFactory, credential)
                    .setApplicationName(applicationName)
                    .build()!!

    @Provides
    @Reusable
    fun provideHttpTransport(): HttpTransport = GoogleNetHttpTransport.newTrustedTransport()

    @Provides
    @Reusable
    fun provideJsonFactory(): JsonFactory = JacksonFactory.getDefaultInstance()

}