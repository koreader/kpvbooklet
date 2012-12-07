INSERT INTO "handlerIds" VALUES('com.lab126.booklet.kpvbooklet');
INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','lipcId','com.lab126.booklet.kpvbooklet');
INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','jar','/opt/amazon/ebook/booklet/KPVBooklet.jar');
INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','detailFactoryPath','/opt/amazon/ebook/lib/detail_view.jar');
INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','detailFactoryClass','com.amazon.ebook.booklet.reader.impl.detail.ReaderDetailViewFactory');

INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','maxGoTime','0');
INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','maxPauseTime','60');

INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','default-chrome-style','NH');
INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','extend-start','Y');
INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','searchbar-mode','transient');
INSERT INTO "properties" VALUES('com.lab126.booklet.kpvbooklet','supportedOrientation','URL');

INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/pdf','true');

INSERT INTO "mimetypes" VALUES('djvu','MT:image/x.djvu');
INSERT INTO "extenstions" VALUES('djvu','MT:image/x.djvu');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','image/x.djvu','DjVu');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.djvu','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:image/x.djvu','true');

INSERT INTO "mimetypes" VALUES('epub','MT:application/epub+zip');
INSERT INTO "extenstions" VALUES('epub','MT:application/epub+zip');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/epub+zip','EPUB');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.epub','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/epub+zip','true');

INSERT INTO "mimetypes" VALUES('cbz','MT:application/cbz');
INSERT INTO "extenstions" VALUES('cbz','MT:application/cbz');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/cbz','CBZ');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.cbz','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/cbz','true');

INSERT INTO "mimetypes" VALUES('chm','MT:application/chm');
INSERT INTO "extenstions" VALUES('chm','MT:application/chm');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/chm','CHM');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.chm','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/chm','true');

INSERT INTO "mimetypes" VALUES('doc','MT:application/doc');
INSERT INTO "extenstions" VALUES('doc','MT:application/doc');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/doc','DOC');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.doc','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/doc','true');

INSERT INTO "mimetypes" VALUES('fb2','MT:application/fb2');
INSERT INTO "extenstions" VALUES('fb2','MT:application/fb2');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/fb2','FB2');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.fb2','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/fb2','true');

INSERT INTO "mimetypes" VALUES('htm','MT:text/htm');
INSERT INTO "extenstions" VALUES('htm','MT:text/htm');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','text/htm','HTML');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.htm','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:text/htm','true');

INSERT INTO "mimetypes" VALUES('html','MT:text/html');
INSERT INTO "extenstions" VALUES('html','MT:text/html');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','text/html','HTML');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.html','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:text/html','true');

INSERT INTO "mimetypes" VALUES('pdb','MT:application/pdb');
INSERT INTO "extenstions" VALUES('pdb','MT:application/pdb');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/pdb','PDB');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.pdb','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/pdb','true');

INSERT INTO "mimetypes" VALUES('rtf','MT:application/rtf');
INSERT INTO "extenstions" VALUES('rtf','MT:application/rtf');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/rtf','RTF');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.rtf','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/rtf','true');

INSERT INTO "mimetypes" VALUES('tcr','MT:application/tcr');
INSERT INTO "extenstions" VALUES('tcr','MT:application/tcr');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/tcr','TCR');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.tcr','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/tcr','true');

INSERT INTO "mimetypes" VALUES('txt','MT:text/plain');
INSERT INTO "extenstions" VALUES('txt','MT:text/plain');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','text/plain','TXT');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.txt','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:text/plain','true');

INSERT INTO "mimetypes" VALUES('xps','MT:application/xps');
INSERT INTO "extenstions" VALUES('xps','MT:application/xps');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/xps','XPS');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.xps','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/xps','true');

INSERT INTO "mimetypes" VALUES('zip','MT:application/zip');
INSERT INTO "extenstions" VALUES('zip','MT:application/zip');
INSERT INTO "properties" VALUES('archive.displaytags.mimetypes','application/zip','ZIP');
INSERT INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.zip','true');
INSERT INTO "associations" VALUES('com.lab126.booklet.kpvbooklet','application','MT:application/zip','true');

