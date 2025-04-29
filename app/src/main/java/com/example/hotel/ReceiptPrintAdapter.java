package com.example.hotel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReceiptPrintAdapter extends PrintDocumentAdapter {
    private Context context;
    private String title, content;
    private PrintedPdfDocument pdfDocument;

    public ReceiptPrintAdapter(Context context, String title, String content) {
        this.context = context;
        this.title = title;
        this.content = content;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        pdfDocument = new PrintedPdfDocument(context, newAttributes);
        PrintDocumentInfo info = new PrintDocumentInfo.Builder("receipt.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .build();
        callback.onLayoutFinished(info, true);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        Canvas canvas = pdfDocument.startPage(0).getCanvas();
        Paint paint = new Paint();
        paint.setTextSize(18);
        canvas.drawText(title, 100, 50, paint);
        canvas.drawText(content, 100, 100, paint);
        pdfDocument.finishPage(pdfDocument.startPage(0));

        try {
            pdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
        callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
    }
}
