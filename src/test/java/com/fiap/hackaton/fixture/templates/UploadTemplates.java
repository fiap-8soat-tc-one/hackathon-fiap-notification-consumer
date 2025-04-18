package com.fiap.hackaton.fixture.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fiap.hackaton.core.domain.entities.Uploads;
import com.fiap.hackaton.domain.enums.UploadStatus;

import java.time.LocalDateTime;

public class UploadTemplates implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Uploads.class).addTemplate("valid", new Rule() {{
            add("id", random("1234567890"));
            add("email", random("file1.txt", "file2.txt"));
            add("status", random(UploadStatus.PENDING.name(), UploadStatus.PROCESSED.name()));
            add("urlDownload", random("http://example.com/file1.txt", "http://example.com/file2.txt"));
            add("createdAt", LocalDateTime.now());
        }});
    }
}
