==================================HEQ 수정 원본==============================================
-- Start of generated script for 203.242.37.23-DB2HEQ-HEQ (db2heq)
--  Oct-26-2014 at 14:52:24

DROP SPECIFIC FUNCTION "SAPHEE"."GET_JAJEA_POOMOK_01";

SET SCHEMA DB2HEQ;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","DB2HEQ";

CREATE FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 ("IN_MANDT" VARCHAR(9),
  "IN_POSID" VARCHAR(72),
  "IN_MATNR" VARCHAR(30),
  "IN_EBELN" VARCHAR(30),
  "IN_EBELP" VARCHAR(15),
  "IN_MENGE" DECIMAL(13, 3)
 ) 
  RETURNS VARCHAR(3)
  SPECIFIC "SAPHEE"."GET_JAJEA_POOMOK_01"
  LANGUAGE SQL
  DETERMINISTIC
  READS SQL DATA
  STATIC DISPATCH
  CALLED ON NULL INPUT
  NO EXTERNAL ACTION
  INHERIT SPECIAL REGISTERS
  BEGIN ATOMIC
    DECLARE P_MANDT VARCHAR(72) DEFAULT '';
    DECLARE P_POSID VARCHAR(72) DEFAULT '';
    DECLARE P_MATNR VARCHAR(72) DEFAULT '';
    DECLARE P_WBS VARCHAR(72) DEFAULT '';
    DECLARE RT VARCHAR(3) DEFAULT '';
    DECLARE LN INTEGER DEFAULT 0;
    SET P_MANDT = RTRIM(IN_MANDT);
    SET P_POSID = RTRIM(IN_POSID);
    SET P_MATNR = RTRIM(IN_MATNR);
    SET LN = LOCATE('-P',P_POSID);
    IF (LN = 0) THEN
      SET P_WBS = P_POSID;
    ELSE
      SET P_WBS = SUBSTR(P_POSID,1,LN-1);
    END IF;
    SET RT = (
      SELECT
          CASE
            WHEN (VALUE(A.MENGE,0) -
                CASE
                  WHEN A.MENGE IS NULL
                    THEN 0
                  ELSE VALUE(C.MENGE,0) + IN_MENGE
                END + VALUE(D.MENGE,0)) <> 0
              THEN 'E'
            WHEN A.PRCTYP = 'C'
              THEN 'C'
            ELSE 'N'
          END
        FROM (
          SELECT SUM( (
              CASE
                WHEN A.PRCTYP='D'
                  THEN 0
                ELSE A.MENGE
              END) * (
              CASE
                WHEN B.PRCTYP IS NULL
                  THEN 1
                WHEN B.PRCTYP='D'
                  THEN 0
                ELSE B.MENGE
              END) ) AS MENGE , MAX(
              CASE
                WHEN A.PRCTYP= 'C'
                  THEN 'C'
                ELSE ''
              END) AS PRCTYP
            FROM (
              SELECT *
                FROM (
                  SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                      ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR,IDNRK,
                      ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK, 
                      ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                    FROM SAPHEE.ZPPT004
                    WHERE MANDT = P_MANDT
                      AND WOKNUM = P_WBS
                      AND IDNRK = P_MATNR ) AS A
                WHERE ROWNO=1 ) AS A
              LEFT OUTER JOIN (
                SELECT *
                  FROM (
                    SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                        ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR, 
                        IDNRK,ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK
                        ,ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                      FROM SAPHEE.ZPPT004 AS A
                      WHERE MANDT = P_MANDT
                        AND WOKNUM = P_WBS
                        --                         AND EXISTS (
                        --                           SELECT 'X'
                        
                          --                             FROM SAPHEE.ZPPT004 AS B
                        --                             WHERE B.MANDT = P_MANDT
                        --                               AND B.WOKNUM = P_WBS
                        --                               AND B.IDNRK = P_MATNR
                        
                          --                               AND B.MATNR = A.IDNRK ) ) AS B
                        AND (MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,
                          ITEM_SEQ) IN (
                          SELECT MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,
                              MATKL,ITEM_SEQ
                            FROM SAPHEE.ZPPT004 AS B
                            WHERE B.MANDT = P_MANDT
                              AND B.WOKNUM = P_WBS
                              AND B.IDNRK = P_MATNR
                              AND B.MATNR = A.IDNRK )) AS B
                  WHERE ROWNO=1 ) AS B
              ON B.MANDT = A.MANDT
              AND B.WOKNUM= A.WOKNUM
              AND B.IDNRK = A.MATNR ) AS A, (
          SELECT SUM(MENGE) AS MENGE
            FROM SAPHEE.ZMMT013
            WHERE MANDT = P_MANDT
              AND POSID = P_POSID
              AND MATNR = P_MATNR
              AND DDATE = '00000000' ) AS C, (
          SELECT SUM(MENGE) AS MENGE
            FROM SAPHEE.ZMMT013
            WHERE MANDT = P_MANDT
              AND DDATE = '00000000'
              AND EBELN = IN_EBELN
              AND EBELP = IN_EBELP ) AS D );
    RETURN RT;
  END;

COMMENT ON FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  IS '자재 품목변경  검사(BOM과 비교)';

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO USER "DB2HEP" WITH GRANT OPTION;

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO PUBLIC;



-- End of generated script for 203.242.37.23-DB2HEQ-HEQ (db2heq)



=====================================20141026 수정 =============================================

-- Start of generated script for 203.242.37.23-DB2HEQ-HEQ (db2heq)
--  Oct-26-2014 at 14:52:24

DROP SPECIFIC FUNCTION "SAPHEE"."GET_JAJEA_POOMOK_01";

SET SCHEMA DB2HEQ;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","DB2HEQ";

CREATE FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 ("IN_MANDT" VARCHAR(9),
  "IN_POSID" VARCHAR(72),
  "IN_MATNR" VARCHAR(30),
  "IN_EBELN" VARCHAR(30),
  "IN_EBELP" VARCHAR(15),
  "IN_MENGE" DECIMAL(13, 3)
 ) 
  RETURNS VARCHAR(3)
  SPECIFIC "SAPHEE"."GET_JAJEA_POOMOK_01"
  LANGUAGE SQL
  NOT DETERMINISTIC
  READS SQL DATA
  STATIC DISPATCH
  CALLED ON NULL INPUT
  EXTERNAL ACTION
  INHERIT SPECIAL REGISTERS
  BEGIN ATOMIC
    DECLARE P_MANDT VARCHAR(72) DEFAULT '';
    DECLARE P_POSID VARCHAR(72) DEFAULT '';
    DECLARE P_MATNR VARCHAR(72) DEFAULT '';
    DECLARE P_WBS VARCHAR(72) DEFAULT '';
    DECLARE RT VARCHAR(3) DEFAULT '';
    DECLARE LN INTEGER DEFAULT 0;
	DECLARE SUB_MA INTEGER DEFAULT 0;
    SET P_MANDT = RTRIM(IN_MANDT);
    SET P_POSID = RTRIM(IN_POSID);
    SET P_MATNR = RTRIM(IN_MATNR);
    SET LN = LOCATE('-P',P_POSID);
    IF (LN = 0) THEN
      SET P_WBS = P_POSID;
    ELSE
      SET P_WBS = SUBSTR(P_POSID,1,LN-1);
    END IF;
	SET SUB_MA =(
		SELECT COUNT(*)
                      FROM SAPHEE.ZPPT004 AS A
                      WHERE MANDT = P_MANDT
                        AND WOKNUM = P_WBS
                        AND (MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,ITEM_SEQ) IN (
                                      SELECT  MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,ITEM_SEQ
                                        FROM SAPHEE.ZPPT004 AS B
                                        WHERE B.MANDT =  P_MANDT
                                          AND B.WOKNUM = P_WBS
                                          AND B.IDNRK = P_MATNR
                                          AND B.MATNR = A.IDNRK )
	);

    IF (SUB_MA = 0) THEN
         SET RT = (
           SELECT
               CASE
                 WHEN (VALUE(A.MENGE,0) -
                     CASE
                       WHEN A.MENGE IS NULL
                         THEN 0
                       ELSE VALUE(C.MENGE,0) + IN_MENGE
                     END + VALUE(D.MENGE,0)) <> 0
                   THEN 'E'
                 WHEN A.PRCTYP = 'C'
                   THEN 'C'
                 ELSE 'N'
               END
             FROM (
               SELECT SUM( 
                   CASE
                     WHEN A.PRCTYP='D'
                       THEN 0
                     ELSE A.MENGE
                   END) AS MENGE , MAX(
                   CASE
                     WHEN A.PRCTYP= 'C'
                       THEN 'C'
                     ELSE ''
                   END) AS PRCTYP
                 FROM (
                   SELECT *
                     FROM (
                       SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                           ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR,IDNRK,
                           ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK, 
                           ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                         FROM SAPHEE.ZPPT004
                         WHERE MANDT = P_MANDT
                           AND WOKNUM = P_WBS
                           AND IDNRK = P_MATNR ) AS A
                         WHERE ROWNO=1 ) AS A
                     ) AS A, (
               SELECT SUM(MENGE) AS MENGE
                 FROM SAPHEE.ZMMT013
                 WHERE MANDT = P_MANDT
                   AND POSID = P_POSID
                   AND MATNR = P_MATNR
                   AND DDATE = '00000000' ) AS C, (
               SELECT SUM(MENGE) AS MENGE
                 FROM SAPHEE.ZMMT013
                 WHERE MANDT = P_MANDT
                   AND DDATE = '00000000'
                   AND EBELN = IN_EBELN
                   AND EBELP = IN_EBELP ) AS D );	
    ELSE
         SET RT = (
           SELECT
               CASE
                 WHEN (VALUE(A.MENGE,0) -
                     CASE
                       WHEN A.MENGE IS NULL
                         THEN 0
                       ELSE VALUE(C.MENGE,0) + IN_MENGE
                     END + VALUE(D.MENGE,0)) <> 0
                   THEN 'E'
                 WHEN A.PRCTYP = 'C'
                   THEN 'C'
                 ELSE 'N'
               END
             FROM (
               SELECT SUM( (
                   CASE
                     WHEN A.PRCTYP='D'
                       THEN 0
                     ELSE A.MENGE
                   END) * (
                   CASE
                     WHEN B.PRCTYP IS NULL
                       THEN 1
                     WHEN B.PRCTYP='D'
                       THEN 0
                     ELSE B.MENGE
                   END) ) AS MENGE , MAX(
                   CASE
                     WHEN A.PRCTYP= 'C'
                       THEN 'C'
                     ELSE ''
                   END) AS PRCTYP
                 FROM (
                   SELECT *
                     FROM (
                       SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                           ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR,IDNRK,
                           ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK, 
                           ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                         FROM SAPHEE.ZPPT004
                         WHERE MANDT = P_MANDT
                           AND WOKNUM = P_WBS
                           AND IDNRK = P_MATNR ) AS A
                     WHERE ROWNO=1 ) AS A
                   LEFT OUTER JOIN (
                     SELECT *
                       FROM (
                         SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                             ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR, 
                             IDNRK,ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK
                             ,ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                           FROM SAPHEE.ZPPT004 AS A
                           WHERE MANDT = P_MANDT
                             AND WOKNUM = P_WBS
                             AND (MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,
                               ITEM_SEQ) IN (
                               SELECT MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,
                                   MATKL,ITEM_SEQ
                                 FROM SAPHEE.ZPPT004 AS B
                                 WHERE B.MANDT = P_MANDT
                                   AND B.WOKNUM = P_WBS
                                   AND B.IDNRK = P_MATNR
                                   AND B.MATNR = A.IDNRK )) AS B
                       WHERE ROWNO=1 ) AS B
                   ON B.MANDT = A.MANDT
                   AND B.WOKNUM= A.WOKNUM
                   AND B.IDNRK = A.MATNR ) AS A, (
               SELECT SUM(MENGE) AS MENGE
                 FROM SAPHEE.ZMMT013
                 WHERE MANDT = P_MANDT
                   AND POSID = P_POSID
                   AND MATNR = P_MATNR
                   AND DDATE = '00000000' ) AS C, (
               SELECT SUM(MENGE) AS MENGE
                 FROM SAPHEE.ZMMT013
                 WHERE MANDT = P_MANDT
                   AND DDATE = '00000000'
                   AND EBELN = IN_EBELN
                   AND EBELP = IN_EBELP ) AS D );	
	END IF;

    RETURN RT;
  END;

COMMENT ON FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  IS '자재 품목변경  검사(BOM과 비교)';

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO USER "DB2HEP" WITH GRANT OPTION;

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO PUBLIC;



-- End of generated script for 203.242.37.23-DB2HEQ-HEQ (db2heq)


===============================테스트 쿼리=======================================
 SELECT
   '0' AS CHECK 
  , MM13.EINDT AS EINDT
  , MM13.EBELN AS EBELN
  , MM13.EBELP AS EBELP
  , MM13.MATNR AS MATNR
  , MM13.TXZ01 AS TXZ01
  , MM13.MENGE AS MENGE
  , MM13.MPASS AS MPASS
  , MM13.MFAIL AS MFAIL
  , MM13.NCRED AS NCRED
  , MM13.NSEND AS NSEND
  , MM13.SEND  AS SEND
  , MM13.POSID AS POSID
  , MM13.POST1 AS POST1
  , MM13.UDATE AS UDATE
  , MM13.SDATE AS SDATE
  , MM13.SHIPDAT AS SHIPDAT 
  , MM13.P_END AS P_END
  , MM13.S_VEND AS S_VEND
  , MM13.BIGO AS BIGO
  , MM13.PERIOD AS PERIOD 
  , CAST(MA01.ZQNTY AS INT) AS ZQNTY
  , MM13.RDATE_P AS RDATE_P
  , MM13.BUDAT AS BUDAT
  , MM13.RECE_DATE AS RECE_DATE
FROM (
	  SELECT
	   MM013.MANDT AS MANDT 
	  ,SAPHEE.GET_ZERODATE(MM013.EINDT) AS EINDT
	  ,MM013.EBELN AS EBELN
	  ,MM013.EBELP AS EBELP
	  ,MM013.MATNR AS MATNR
	  ,MM013.TXZ01 AS TXZ01
	  ,COALESCE(MM013.MENGE, 0) AS MENGE
	  ,COALESCE(MM017A.MPASS, 0) AS MPASS
	  ,COALESCE(MM017A.MFAIL, 0) AS MFAIL
	  ,COALESCE((MM013.MENGE - COALESCE((MM017A.MPASS+MM017A.NSEND+MM017A.SEND), 0)), 0) AS NCRED
	  ,COALESCE(MM017A.NSEND, 0) AS NSEND
	  ,COALESCE(MM017A.SEND, 0) AS SEND
	  ,MM013.POSID AS POSID
	  ,MM013.POST1 AS POST1
	  ,MM013.BOX_CK AS BOX_CK
	  ,MM013.EXTWG AS EXTWG
	  ,SAPHEE.GET_ZERODATE(MM013.UDATE) AS UDATE
	  ,SAPHEE.GET_ZERODATE(MM013.SDATE) AS SDATE
	  ,SAPHEE.GET_ZERODATE(MM013.RDATE_P) AS RDATE_P

           -- NB,NS 의 경우 PRPS에서 출하일 가져옴 2014.05.16 HSS
           , CASE WHEN SUBSTR(MM013.POSID,7,2) IN ('NB','NS') THEN (CASE WHEN MM013.EXTWG IS NULL OR MM013.EXTWG = '' OR (MM013.BOX_CK = '' AND MM013.EXTWG = 'BOX33') OR (MM013.BOX_CK = '24' AND MM013.EXTWG='BOX24') THEN 
         	  (CASE WHEN PR.ZZPRO01  IS NULL    THEN '00000000'
                   WHEN MM011.BLOCK_NO = ''       THEN PR.ZZPRO02 
                   WHEN MM011.BLOCK_NO LIKE 'A%'  THEN PR.ZZPRO01 
                   WHEN MM011.BLOCK_NO LIKE 'B%'  THEN PR.ZZPRO02 
                   WHEN MM011.BLOCK_NO LIKE 'C%'  THEN PR.ZZPRO03 
                   WHEN MM011.BLOCK_NO LIKE 'D%'  THEN PR.ZZPRO04 
                   WHEN MM011.BLOCK_NO LIKE 'E%'  THEN PR.ZZPRO05 ELSE '00000000' END) 
         	   ELSE		   
         	   (CASE ( CASE WHEN MM013.BOX_CK = '24' THEN  (SELECT VALUE(SUBSTR(BLOCK,1,1),'') FROM SAPHEE.ZMMT026_1 WHERE MANDT= MM013.MANDT AND BOXNO = MM013.EXTWG)
                                     ELSE  (SELECT VALUE(SUBSTR(BLOCK,1,1),'') FROM SAPHEE.ZMMT026 WHERE MANDT= MM013.MANDT AND BOXNO = MM013.EXTWG) END )
         		    WHEN '1' THEN PR.ZZPRO01 
         		    WHEN '2' THEN PR.ZZPRO02 
         		    WHEN '3' THEN PR.ZZPRO03 
         		    WHEN '4' THEN PR.ZZPRO04 
         		    WHEN '5' THEN PR.ZZPRO05 ELSE '00000000' END)  END )
              ELSE (
                CASE WHEN MM013.EXTWG IS NULL OR MM013.EXTWG = '' OR (MM013.BOX_CK = '' AND MM013.EXTWG = 'BOX33') OR (MM013.BOX_CK = '24' AND MM013.EXTWG='BOX24') THEN 
         	    (CASE WHEN MM025.SHIPDAT1 IS NULL    THEN '00000000'
                   WHEN MM011.BLOCK_NO = ''       THEN MM025.SHIPDAT2
                   WHEN MM011.BLOCK_NO LIKE 'A%'  THEN MM025.SHIPDAT1
                   WHEN MM011.BLOCK_NO LIKE 'B%'  THEN MM025.SHIPDAT2 
                   WHEN MM011.BLOCK_NO LIKE 'C%'  THEN MM025.SHIPDAT3  
                   WHEN MM011.BLOCK_NO LIKE 'D%'  THEN MM025.SHIPDAT4  
                   WHEN MM011.BLOCK_NO LIKE 'E%'  THEN MM025.SHIPDAT5 ELSE '00000000' END) 
         	   ELSE		   
         	   (CASE ( CASE WHEN MM013.BOX_CK = '24' THEN  (SELECT VALUE(SUBSTR(BLOCK,1,1),'') FROM SAPHEE.ZMMT026_1 WHERE MANDT= MM013.MANDT AND BOXNO = MM013.EXTWG)
                                     ELSE  (SELECT VALUE(SUBSTR(BLOCK,1,1),'') FROM SAPHEE.ZMMT026 WHERE MANDT= MM013.MANDT AND BOXNO = MM013.EXTWG) END )
         		    WHEN '1' THEN MM025.SHIPDAT1
         		    WHEN '2' THEN MM025.SHIPDAT2
         		    WHEN '3' THEN MM025.SHIPDAT3
         		    WHEN '4' THEN MM025.SHIPDAT4
         		    WHEN '5' THEN MM025.SHIPDAT5  ELSE '00000000' END)  END
              ) END 
                  AS SHIPDAT 

              , VALUE(SAPHEE.GET_PROJ_END(MM013.MANDT,MM013.POSID ),'') AS P_END
              , VALUE(SAPHEE.GET_SEOLCHI_VEND_NAME(MM013.MANDT,MM013.POSID ),'') AS S_VEND
            --  , CASE WHEN (MM013.WERKS <> '2000' AND SAPHEE.GET_JAJEA_BALJU(MM013.MANDT, MM013.POSID,MM013.MATNR) <> 0) THEN '1' ELSE '0' END   AS BIGO
              , CASE WHEN MM013.WERKS <> '2000' THEN SAPHEE.GET_JAJEA_POOMOK(MM013.MANDT, MM013.POSID,MM013.MATNR) ELSE 'N' END   AS BIGO
           
              , SAPHEE.GET_DAYS(MM013.SDATE, SUBSTR(SAPHEE.TO_DATETIME(),1,8)) AS PERIOD
	  ,SAPHEE.GET_ZERODATE(MM017A.BUDAT) AS BUDAT
	  ,SAPHEE.GET_ZERODATE(MM017A.RECE_DATE) AS RECE_DATE

	 FROM SAPHEE.ZMMT013 AS MM013
		   LEFT OUTER JOIN ( 

		                SELECT 
		                 MANDT AS MANDT
		                ,EBELN AS EBELN
		                ,EBELP AS EBELP
		                ,SUM(NSEND) AS NSEND
		                ,SUM(SEND) AS SEND
		                ,SUM(MPASS) AS MPASS
		                ,SUM(MFAIL) AS MFAIL
                                            ,MAX(BUDAT)  AS BUDAT
                                            ,MAX(RECE_DATE)  AS RECE_DATE
						FROM (
		                	 SELECT 
		                 	 MM017.MANDT AS MANDT
		                	 ,MM017.EBELN AS EBELN
		                	 ,MM017.EBELP AS EBELP
		                	 ,CASE WHEN PRO_F = '1' THEN COALESCE(SUM(MM017.MENGE),0) ELSE 0 END AS NSEND
		                	 ,CASE WHEN PRO_F = '2' THEN COALESCE(SUM(MM017.MENGE),0) ELSE 0 END AS SEND
		                	 ,CASE WHEN PRO_F = '4' THEN COALESCE(SUM(MM017.MENGE),0) ELSE 0 END AS MPASS
		                	 ,CASE WHEN PRO_F = '4' THEN COALESCE(SUM(MM017.B_MENGE),0) ELSE 0 END AS MFAIL
                                                         , MAX(MM017.BUDAT) AS BUDAT
                                                         , MAX(MM017.RECE_DATE) AS RECE_DATE
		                FROM SAPHEE.ZMMT017 AS MM017
	                   WHERE MM017.MANDT = '183'
	                     AND MM017.LIFNR = '2188115501'
	                      	   AND MM017.UDATE BETWEEN '20141026' AND '20141110'
	                                            AND MM017.PRO_F >= '1'
	                   GROUP BY MM017.MANDT,MM017.EBELN,MM017.EBELP,MM017.PRO_F) AS S
                               WHERE 1=1
		   GROUP BY S.MANDT,S.EBELN,S.EBELP
	           			  
		 ) AS MM017A ON  MM013.MANDT = MM017A.MANDT 
	                AND MM013.EBELN = MM017A.EBELN
	                AND MM013.EBELP = MM017A.EBELP

		 INNER JOIN SAPHEE.ZMMT011      AS MM011 ON  MM013.MANDT = MM011.MANDT
		                                         AND MM013.MATNR = MM011.MATNR

--		 LEFT OUTER JOIN SAPHEE.ZMMT026 AS MM026 ON  MM011.MANDT = MM026.MANDT
--		                     AND MM011.EXTWG = MM026.BOXNO

		 LEFT OUTER JOIN SAPHEE.ZMMT025 AS MM025 ON  MM013.MANDT = MM025.MANDT
		                                         AND SUBSTR(MM013.POSID,1,9) = MM025.POSID
		                                         AND MM025.PSTYPE = '02'

                                -- NB,NS 의 경우 PRPS에서 출하일 가져옴 2014.05.16 HSS
                              LEFT OUTER JOIN SAPHEE.PRPS AS PR ON PR.MANDT= MM013.MANDT
                                                                                   AND PR.POSID = MM013.POSID


	 WHERE MM013.MANDT = '183' 
	   AND MM013.LIFNR = '2188115501'
	   AND MM013.STATE = '2'
     AND MM013.UDATE BETWEEN '20141026' AND '20141110'
  ) AS MM13
  LEFT OUTER JOIN SAPHEE.ZMASTER01 AS MA01 ON MA01.MANDT= MM13.MANDT
	                                             AND MA01.POSID = (CASE WHEN SUBSTR(MM13.POSID,1,2) = 'QM' THEN SUBSTR(MM13.POSID,1,8)
                                                                                           WHEN SUBSTR(MM13.POSID,1,1) = 'Q' THEN SUBSTR(MM13.POSID,1,7)
                                                                                  ELSE SUBSTR(MM13.POSID,1,6) END)  

WHERE 1= 1
 ORDER BY UDATE,POSID,MATNR
FOR FETCH ONLY
WITH UR	


==========================================운영 적용 ============================================================

DROP SPECIFIC FUNCTION "SAPHEE"."GET_JAJEA_POOMOK_01";

SET SCHEMA DB2HEP;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","DB2HEP";

CREATE FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 ("IN_MANDT" VARCHAR(9),
  "IN_POSID" VARCHAR(72),
  "IN_MATNR" VARCHAR(30),
  "IN_EBELN" VARCHAR(30),
  "IN_EBELP" VARCHAR(15),
  "IN_MENGE" DECIMAL(13, 3)
 ) 
  RETURNS VARCHAR(3)
  SPECIFIC "SAPHEE"."GET_JAJEA_POOMOK_01"
  LANGUAGE SQL
  DETERMINISTIC
  READS SQL DATA
  STATIC DISPATCH
  CALLED ON NULL INPUT
  NO EXTERNAL ACTION
  INHERIT SPECIAL REGISTERS
  BEGIN ATOMIC
    DECLARE P_MANDT VARCHAR(72) DEFAULT '';
    DECLARE P_POSID VARCHAR(72) DEFAULT '';
    DECLARE P_MATNR VARCHAR(72) DEFAULT '';
    DECLARE P_WBS VARCHAR(72) DEFAULT '';
    DECLARE RT VARCHAR(3) DEFAULT '';
    DECLARE LN INTEGER DEFAULT 0;
	DECLARE SUB_MA INTEGER DEFAULT 0;
    SET P_MANDT = RTRIM(IN_MANDT);
    SET P_POSID = RTRIM(IN_POSID);
    SET P_MATNR = RTRIM(IN_MATNR);
    SET LN = LOCATE('-P',P_POSID);
    IF (LN = 0) THEN
      SET P_WBS = P_POSID;
    ELSE
      SET P_WBS = SUBSTR(P_POSID,1,LN-1);
    END IF;
	SET SUB_MA =(
		SELECT COUNT(*)
                      FROM SAPHEE.ZPPT004 AS A
                      WHERE MANDT = P_MANDT
                        AND WOKNUM = P_WBS
                        AND (MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,ITEM_SEQ) IN (
                                      SELECT  MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,ITEM_SEQ
                                        FROM SAPHEE.ZPPT004 AS B
                                        WHERE B.MANDT =  P_MANDT
                                          AND B.WOKNUM = P_WBS
                                          AND B.IDNRK = P_MATNR
                                          AND B.MATNR = A.IDNRK )
	);

    IF (SUB_MA = 0) THEN
         SET RT = (
           SELECT
               CASE
                 WHEN (VALUE(A.MENGE,0) -
                     CASE
                       WHEN A.MENGE IS NULL
                         THEN 0
                       ELSE VALUE(C.MENGE,0) + IN_MENGE
                     END + VALUE(D.MENGE,0)) <> 0
                   THEN 'E'
                 WHEN A.PRCTYP = 'C'
                   THEN 'C'
                 ELSE 'N'
               END
             FROM (
               SELECT SUM( 
                   CASE
                     WHEN A.PRCTYP='D'
                       THEN 0
                     ELSE A.MENGE
                   END) AS MENGE , MAX(
                   CASE
                     WHEN A.PRCTYP= 'C'
                       THEN 'C'
                     ELSE ''
                   END) AS PRCTYP
                 FROM (
                   SELECT *
                     FROM (
                       SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                           ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR,IDNRK,
                           ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK, 
                           ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                         FROM SAPHEE.ZPPT004
                         WHERE MANDT = P_MANDT
                           AND WOKNUM = P_WBS
                           AND IDNRK = P_MATNR ) AS A
                         WHERE ROWNO=1 ) AS A
                     ) AS A, (
               SELECT SUM(MENGE) AS MENGE
                 FROM SAPHEE.ZMMT013
                 WHERE MANDT = P_MANDT
                   AND POSID = P_POSID
                   AND MATNR = P_MATNR
                   AND DDATE = '00000000' ) AS C, (
               SELECT SUM(MENGE) AS MENGE
                 FROM SAPHEE.ZMMT013
                 WHERE MANDT = P_MANDT
                   AND DDATE = '00000000'
                   AND EBELN = IN_EBELN
                   AND EBELP = IN_EBELP ) AS D );	
    ELSE
         SET RT = (
           SELECT
               CASE
                 WHEN (VALUE(A.MENGE,0) -
                     CASE
                       WHEN A.MENGE IS NULL
                         THEN 0
                       ELSE VALUE(C.MENGE,0) + IN_MENGE
                     END + VALUE(D.MENGE,0)) <> 0
                   THEN 'E'
                 WHEN A.PRCTYP = 'C'
                   THEN 'C'
                 ELSE 'N'
               END
             FROM (
               SELECT SUM( (
                   CASE
                     WHEN A.PRCTYP='D'
                       THEN 0
                     ELSE A.MENGE
                   END) * (
                   CASE
                     WHEN B.PRCTYP IS NULL
                       THEN 1
                     WHEN B.PRCTYP='D'
                       THEN 0
                     ELSE B.MENGE
                   END) ) AS MENGE , MAX(
                   CASE
                     WHEN A.PRCTYP= 'C'
                       THEN 'C'
                     ELSE ''
                   END) AS PRCTYP
                 FROM (
                   SELECT *
                     FROM (
                       SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                           ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR,IDNRK,
                           ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK, 
                           ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                         FROM SAPHEE.ZPPT004
                         WHERE MANDT = P_MANDT
                           AND WOKNUM = P_WBS
                           AND IDNRK = P_MATNR ) AS A
                     WHERE ROWNO=1 ) AS A
                   LEFT OUTER JOIN (
                     SELECT *
                       FROM (
                         SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                             ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR, 
                             IDNRK,ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK
                             ,ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                           FROM SAPHEE.ZPPT004 AS A
                           WHERE MANDT = P_MANDT
                             AND WOKNUM = P_WBS
                             AND (MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,
                               ITEM_SEQ) IN (
                               SELECT MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,
                                   MATKL,ITEM_SEQ
                                 FROM SAPHEE.ZPPT004 AS B
                                 WHERE B.MANDT = P_MANDT
                                   AND B.WOKNUM = P_WBS
                                   AND B.IDNRK = P_MATNR
                                   AND B.MATNR = A.IDNRK )) AS B
                       WHERE ROWNO=1 ) AS B
                   ON B.MANDT = A.MANDT
                   AND B.WOKNUM= A.WOKNUM
                   AND B.IDNRK = A.MATNR ) AS A, (
               SELECT SUM(MENGE) AS MENGE
                 FROM SAPHEE.ZMMT013
                 WHERE MANDT = P_MANDT
                   AND POSID = P_POSID
                   AND MATNR = P_MATNR
                   AND DDATE = '00000000' ) AS C, (
               SELECT SUM(MENGE) AS MENGE
                 FROM SAPHEE.ZMMT013
                 WHERE MANDT = P_MANDT
                   AND DDATE = '00000000'
                   AND EBELN = IN_EBELN
                   AND EBELP = IN_EBELP ) AS D );	
	END IF;

    RETURN RT;
  END;

COMMENT ON FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  IS '자재 품목변경  검사(BOM과 비교)';

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO USER "DB2HEP" WITH GRANT OPTION;

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_POOMOK"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO PUBLIC;