package org.thoughtcrime.securesms.conversation.v2.messages

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_untrusted_attachment.view.*
import network.loki.messenger.R
import org.session.libsession.utilities.recipients.Recipient
import org.thoughtcrime.securesms.conversation.v2.dialogs.DownloadDialog
import org.thoughtcrime.securesms.loki.utilities.ActivityDispatcher
import java.util.*

class UntrustedAttachmentView: LinearLayout {

    enum class AttachmentType {
        AUDIO,
        DOCUMENT,
        MEDIA
    }

    // region Lifecycle
    constructor(context: Context) : super(context) { initialize() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { initialize() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize() }

    private fun initialize() {
        LayoutInflater.from(context).inflate(R.layout.view_untrusted_attachment, this)
    }
    // endregion

    // region Updating
    fun bind(attachmentType: AttachmentType, @ColorInt textColor: Int) {
        val (iconRes, stringRes) = when (attachmentType) {
            AttachmentType.AUDIO -> R.drawable.ic_microphone to R.string.Slide_audio
            AttachmentType.DOCUMENT -> R.drawable.ic_document_large_light to R.string.document
            AttachmentType.MEDIA -> R.drawable.ic_image_white_24dp to R.string.media
        }
        val iconDrawable = ContextCompat.getDrawable(context,iconRes)!!
        iconDrawable.mutate().setTint(textColor)
        val text = context.getString(R.string.UntrustedAttachmentView_download_attachment, context.getString(stringRes).toLowerCase(Locale.ROOT))

        untrustedAttachmentIcon.setImageDrawable(iconDrawable)
        untrustedAttachmentTitle.text = text
    }
    // endregion

    // region Interaction
    fun showTrustDialog(recipient: Recipient) {
        ActivityDispatcher.get(context)?.showDialog(DownloadDialog(recipient))
    }

}