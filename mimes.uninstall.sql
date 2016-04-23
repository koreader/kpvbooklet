DELETE FROM "handlerIds" WHERE handlerId='com.github.koreader.kpvbooklet' or handlerId='com.lab126.booklet.kpvbooklet';
DELETE FROM "properties" WHERE handlerId='com.github.koreader.kpvbooklet' or handlerId='com.lab126.booklet.kpvbooklet';
DELETE FROM "associations" WHERE handlerId='com.github.koreader.kpvbooklet' or handlerId='com.lab126.booklet.kpvbooklet';

DELETE FROM "mimetypes" WHERE ext='djvu' OR ext='epub' OR ext='cbz' OR ext='chm' OR ext='doc';
DELETE FROM "mimetypes" WHERE ext='fb2' OR ext='pdb' OR ext='rtf' OR ext='tcr' OR ext='xps' OR ext='zip';
DELETE FROM "extenstions" WHERE ext='djvu' OR ext='epub' OR ext='cbz' OR ext='chm' OR ext='doc';
DELETE FROM "extenstions" WHERE ext='fb2' OR ext='pdb' OR ext='rtf' OR ext='tcr' OR ext='xps' OR ext='zip';
DELETE FROM "properties" WHERE value='DjVu' OR value='EPUB' OR value='CBZ' OR value='CHM' OR value='DOC';
DELETE FROM "properties" WHERE value='FB2' OR value='PDB' OR value='RTF' OR value='TCR' OR value='XPS' OR value='ZIP';
DELETE FROM "associations" WHERE contentId='GL:*.djvu' OR contentId='GL:*.epub' OR contentId='GL:*.cbz' OR contentId='GL:*.chm';
DELETE FROM "associations" WHERE contentId='GL:*.doc' OR contentId='GL:*.fb2' OR contentId='GL:*.pdb' OR contentId='GL:*.rtf';
DELETE FROM "associations" WHERE contentId='GL:*.tcr' OR contentId='GL:*.xps' OR contentId='GL:*.zip';

UPDATE "associations" SET defaultAssoc = 'true' WHERE interface = 'application' and contentId = 'MT:application/pdf' and handlerId = 'com.lab126.booklet.reader';
