http://www.geocities.jp/dmdottostore/tckool/framepage.html
上のURLの"VX用近未来系?一括DL"からダウンロードしたフォルダを解凍して
srcの隣に置き、srcのとなりにmapフォルダを作成し実行すればマップチップが再配置される。
マップチップサイズは 16*16
mapIDとマップチップの右上座標の対応は
(x, y) = ((mapID % 256)*16, (mapID / 256)*16)
である。
mapID 65535は何も置いていないものとして扱う。