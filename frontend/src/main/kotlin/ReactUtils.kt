import kotlinx.css.CSSBuilder
import kotlinx.css.RuleSet
import notistack.SnackbarVariant
import notistack.WithSnackbar
import notistack.enqueueSnackbar
import notistack.variant
import org.w3c.dom.events.MouseEvent
import react.RElementBuilder
import react.dom.RDOMBuilder
import react.dom.jsStyle
import styled.toStyle
import kotlin.js.Promise

fun RElementBuilder<*>.css(handler: RuleSet) {
    attrs.asDynamic().style = CSSBuilder().apply(handler).toStyle()
}

fun RDOMBuilder<*>.css(handler: RuleSet) {
    attrs.jsStyle = CSSBuilder().apply(handler).toStyle()
}

fun RElementBuilder<*>.onClick(onClick: (MouseEvent) -> Unit) {
    attrs.asDynamic().onClick = onClick
}

fun <T, S> Promise<T>.finally(onFinally: ((T) -> S)?): Promise<S> =
    asDynamic().finally(onFinally) as Promise<S>

fun Promise<*>.snackbarIfError(props: WithSnackbar) = apply {
    catch { props.enqueueSnackbar(toString()) { variant = SnackbarVariant.ERROR } }
}