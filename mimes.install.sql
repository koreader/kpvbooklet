INSERT OR IGNORE INTO "handlerIds" VALUES('com.github.koreader.kpvbooklet');
INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','lipcId','com.github.koreader.kpvbooklet');
INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','jar','/opt/amazon/ebook/booklet/KPVBooklet.jar');
INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','detailFactoryPath','/opt/amazon/ebook/lib/detail_view.jar');
INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','detailFactoryClass','com.amazon.ebook.booklet.reader.impl.detail.ReaderDetailViewFactory');

INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','maxGoTime','0');
INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','maxPauseTime','60');

INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','default-chrome-style','NH');
INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','extend-start','Y');
INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','searchbar-mode','transient');
INSERT OR IGNORE INTO "properties" VALUES('com.github.koreader.kpvbooklet','supportedOrientation','URL');

INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','detail','MT:application/octet-stream','true');

INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/pdf','true');

INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:text/plain','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('djvu','MT:image/x.djvu');
INSERT OR IGNORE INTO "extenstions" VALUES('djvu','MT:image/x.djvu');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','image/x.djvu','DjVu');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.djvu','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:image/x.djvu','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('epub','MT:application/epub+zip');
INSERT OR IGNORE INTO "extenstions" VALUES('epub','MT:application/epub+zip');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/epub+zip','EPUB');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.epub','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/epub+zip','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('cbz','MT:application/cbz');
INSERT OR IGNORE INTO "extenstions" VALUES('cbz','MT:application/cbz');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/cbz','CBZ');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.cbz','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/cbz','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('chm','MT:application/chm');
INSERT OR IGNORE INTO "extenstions" VALUES('chm','MT:application/chm');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/chm','CHM');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.chm','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/chm','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('doc','MT:application/doc');
INSERT OR IGNORE INTO "extenstions" VALUES('doc','MT:application/doc');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/doc','DOC');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.doc','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/doc','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('fb2','MT:application/fb2');
INSERT OR IGNORE INTO "extenstions" VALUES('fb2','MT:application/fb2');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/fb2','FB2');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.fb2','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/fb2','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('pdb','MT:application/pdb');
INSERT OR IGNORE INTO "extenstions" VALUES('pdb','MT:application/pdb');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/pdb','PDB');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.pdb','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/pdb','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('rtf','MT:application/rtf');
INSERT OR IGNORE INTO "extenstions" VALUES('rtf','MT:application/rtf');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/rtf','RTF');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.rtf','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/rtf','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('tcr','MT:application/tcr');
INSERT OR IGNORE INTO "extenstions" VALUES('tcr','MT:application/tcr');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/tcr','TCR');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.tcr','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/tcr','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('xps','MT:application/xps');
INSERT OR IGNORE INTO "extenstions" VALUES('xps','MT:application/xps');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/xps','XPS');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.xps','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/xps','true');

INSERT OR IGNORE INTO "mimetypes" VALUES('zip','MT:application/zip');
INSERT OR IGNORE INTO "extenstions" VALUES('zip','MT:application/zip');
INSERT OR IGNORE INTO "properties" VALUES('archive.displaytags.mimetypes','application/zip','ZIP');
INSERT OR IGNORE INTO "associations" VALUES('com.lab126.generic.extractor','extractor','GL:*.zip','true');
INSERT OR IGNORE INTO "associations" VALUES('com.github.koreader.kpvbooklet','application','MT:application/zip','true');

UPDATE "associations" SET defaultAssoc = 'false' WHERE interface = 'application' and contentId = 'MT:application/pdf' and handlerId = 'com.lab126.booklet.reader';
UPDATE "associations" SET defaultAssoc = 'true' WHERE interface = 'application' and contentId = 'MT:application/pdf' and handlerId = 'com.github.koreader.kpvbooklet';

UPDATE "associations" SET defaultAssoc = 'false' WHERE interface = 'application' and contentId = 'MT:text/plain' and handlerId = 'com.lab126.booklet.reader';
UPDATE "associations" SET defaultAssoc = 'true' WHERE interface = 'application' and contentId = 'MT:text/plain' and handlerId = 'com.github.koreader.kpvbooklet';
