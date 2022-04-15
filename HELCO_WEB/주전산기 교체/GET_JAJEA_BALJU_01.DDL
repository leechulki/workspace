DROP SPECIFIC FUNCTION "SAPHEE"."GET_JAJEA_BALJU_01";

SET SCHEMA DB2HEQ;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","DB2HEQ";

CREATE FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 ("IN_MANDT" VARCHAR(9),
  "IN_POSID" VARCHAR(72),
  "IN_MATNR" VARCHAR(30),
  "IN_EBELN" VARCHAR(30),
  "IN_EBELP" VARCHAR(15),
  "IN_MENGE" DECIMAL(13, 3)
 ) 
  RETURNS DECIMAL(13, 3)
  SPECIFIC "SAPHEE"."GET_JAJEA_BALJU_01"
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
    DECLARE RT DECIMAL(13, 3) DEFAULT 0.0;
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
      SELECT VALUE(A.MENGE,0) -
          CASE
            WHEN A.MENGE IS NULL
              THEN 0
            ELSE VALUE(C.MENGE,0) + IN_MENGE
          END + VALUE(D.MENGE,0)
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
              END) ) AS MENGE
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
                        AND EXISTS (
                          SELECT 'X'
                            FROM SAPHEE.ZPPT004 AS B
                            WHERE B.MANDT = P_MANDT
                              AND B.WOKNUM = P_WBS
                              AND B.IDNRK = P_MATNR
                              AND B.MATNR = A.IDNRK ) ) AS B
                  WHERE ROWNO=1 ) AS B
              ON B.MANDT = A.MANDT
              AND B.WOKNUM= A.WOKNUM
              AND B.IDNRK = A.MATNR ) AS A, (
          SELECT SUM(MENGE) AS MENGE
            FROM SAPHEE.ZMMT013
            WHERE MANDT = P_MANDT
              AND POSID = P_POSID
              AND MATNR = P_MATNR
              AND DDATE = '00000000'
              AND WERKS = '1000') AS C, (
          SELECT SUM(MENGE) AS MENGE
            FROM SAPHEE.ZMMT013
            WHERE MANDT = P_MANDT
              AND EBELN = IN_EBELN
              AND EBELP = IN_EBELP
              AND DDATE = '00000000'
              AND WERKS = '1000') AS D );
    RETURN RT;
  END;

COMMENT ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  IS '자재발주 수량 검사(BOM과 비교)';

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO PUBLIC;

  
  ===================================== SQL 수정=============================================
  
DROP SPECIFIC FUNCTION "SAPHEE"."GET_JAJEA_BALJU_01";

SET SCHEMA DB2HEQ;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","DB2HEQ";

CREATE FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 ("IN_MANDT" VARCHAR(9),
  "IN_POSID" VARCHAR(72),
  "IN_MATNR" VARCHAR(30),
  "IN_EBELN" VARCHAR(30),
  "IN_EBELP" VARCHAR(15),
  "IN_MENGE" DECIMAL(13, 3)
 ) 
  RETURNS DECIMAL(13, 3)
  SPECIFIC "SAPHEE"."GET_JAJEA_BALJU_01"
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
    DECLARE RT DECIMAL(13, 3) DEFAULT 0.0;
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
                        AND EXISTS (
                          SELECT 'X'
                            FROM SAPHEE.ZPPT004 AS B
                            WHERE B.MANDT = P_MANDT
                              AND B.WOKNUM = P_WBS
                              AND B.IDNRK = P_MATNR
                              AND B.MATNR = A.IDNRK )
	);
	
    IF (SUB_MA = 0) THEN
        SET RT = (
          SELECT VALUE(A.MENGE,0) -
              CASE
                WHEN A.MENGE IS NULL
                  THEN 0
                ELSE VALUE(C.MENGE,0) + IN_MENGE
              END + VALUE(D.MENGE,0)
            FROM (
              SELECT SUM( (
                  CASE
                    WHEN A.PRCTYP='D'
                      THEN 0
                    ELSE A.MENGE
                  END)) AS MENGE
                    FROM (
                      SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                          ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR,IDNRK,
                          ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK, 
                          ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                        FROM SAPHEE.ZPPT004
                        WHERE MANDT = P_MANDT
                          AND WOKNUM = P_WBS
                          AND IDNRK = P_MATNR ) AS A
                    WHERE ROWNO=1 ) AS A, (
              SELECT SUM(MENGE) AS MENGE
                FROM SAPHEE.ZMMT013
                WHERE MANDT = P_MANDT
                  AND POSID = P_POSID
                  AND MATNR = P_MATNR
                  AND DDATE = '00000000'
                  AND WERKS = '1000') AS C, (
              SELECT SUM(MENGE) AS MENGE
                FROM SAPHEE.ZMMT013
                WHERE MANDT = P_MANDT
                  AND EBELN = IN_EBELN
                  AND EBELP = IN_EBELP
                  AND DDATE = '00000000'
                  AND WERKS = '1000') AS D );
    ELSE
        SET RT = (
          SELECT VALUE(A.MENGE,0) -
              CASE
                WHEN A.MENGE IS NULL
                  THEN 0
                ELSE VALUE(C.MENGE,0) + IN_MENGE
              END + VALUE(D.MENGE,0)
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
                  END) ) AS MENGE
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
                            AND EXISTS (
                              SELECT 'X'
                                FROM SAPHEE.ZPPT004 AS B
                                WHERE B.MANDT = P_MANDT
                                  AND B.WOKNUM = P_WBS
                                  AND B.IDNRK = P_MATNR
                                  AND B.MATNR = A.IDNRK ) ) AS B
                      WHERE ROWNO=1 ) AS B
                  ON B.MANDT = A.MANDT
                  AND B.WOKNUM= A.WOKNUM
                  AND B.IDNRK = A.MATNR ) AS A, (
              SELECT SUM(MENGE) AS MENGE
                FROM SAPHEE.ZMMT013
                WHERE MANDT = P_MANDT
                  AND POSID = P_POSID
                  AND MATNR = P_MATNR
                  AND DDATE = '00000000'
                  AND WERKS = '1000') AS C, (
              SELECT SUM(MENGE) AS MENGE
                FROM SAPHEE.ZMMT013
                WHERE MANDT = P_MANDT
                  AND EBELN = IN_EBELN
                  AND EBELP = IN_EBELP
                  AND DDATE = '00000000'
                  AND WERKS = '1000') AS D );
    END IF;

    RETURN RT;
  END;

COMMENT ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  IS '자재발주 수량 검사(BOM과 비교)';

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO PUBLIC;
======================================여러건에서도 속도 개선 : 20141025 HEQ 적용 버전 ===================================================


-- Start of generated script for 203.242.37.23-DB2HEQ-HEQ (db2heq)
--  Oct-26-2014 at 14:16:09

DROP SPECIFIC FUNCTION "SAPHEE"."GET_JAJEA_BALJU_01";

SET SCHEMA DB2HEQ;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","DB2HEQ";

CREATE FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 ("IN_MANDT" VARCHAR(9),
  "IN_POSID" VARCHAR(72),
  "IN_MATNR" VARCHAR(30),
  "IN_EBELN" VARCHAR(30),
  "IN_EBELP" VARCHAR(15),
  "IN_MENGE" DECIMAL(13, 3)
 ) 
  RETURNS DECIMAL(13, 3)
  SPECIFIC "SAPHEE"."GET_JAJEA_BALJU_01"
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
    DECLARE RT DECIMAL(13, 3) DEFAULT 0.0;
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
      SELECT VALUE(A.MENGE,0) -
          CASE
            WHEN A.MENGE IS NULL
              THEN 0
            ELSE VALUE(C.MENGE,0) + IN_MENGE
          END + VALUE(D.MENGE,0)
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
              END) ) AS MENGE
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
              AND DDATE = '00000000'
              AND WERKS = '1000') AS C, (
          SELECT SUM(MENGE) AS MENGE
            FROM SAPHEE.ZMMT013
            WHERE MANDT = P_MANDT
              AND EBELN = IN_EBELN
              AND EBELP = IN_EBELP
              AND DDATE = '00000000'
              AND WERKS = '1000') AS D );
    RETURN RT;
  END;

COMMENT ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  IS '자재발주 수량 검사(BOM과 비교)';

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO PUBLIC;



-- End of generated script for 203.242.37.23-DB2HEQ-HEQ (db2heq)

=======================================모자재 검색의 row_number 처리 개선 20141026 =======================================================


DROP SPECIFIC FUNCTION "SAPHEE"."GET_JAJEA_BALJU_01";

SET SCHEMA DB2HEQ;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","DB2HEQ";

CREATE FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 ("IN_MANDT" VARCHAR(9),
  "IN_POSID" VARCHAR(72),
  "IN_MATNR" VARCHAR(30),
  "IN_EBELN" VARCHAR(30),
  "IN_EBELP" VARCHAR(15),
  "IN_MENGE" DECIMAL(13, 3)
 ) 
  RETURNS DECIMAL(13, 3)
  SPECIFIC "SAPHEE"."GET_JAJEA_BALJU_01"
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
    DECLARE RT DECIMAL(13, 3) DEFAULT 0.0;
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
                        AND EXISTS (
                          SELECT 'X'
                            FROM SAPHEE.ZPPT004 AS B
                            WHERE B.MANDT = P_MANDT
                              AND B.WOKNUM = P_WBS
                              AND B.IDNRK = P_MATNR
                              AND B.MATNR = A.IDNRK )
	);
	
    IF (SUB_MA = 0) THEN
        SET RT = (
          SELECT VALUE(A.MENGE,0) -
              CASE
                WHEN A.MENGE IS NULL
                  THEN 0
                ELSE VALUE(C.MENGE,0) + IN_MENGE
              END + VALUE(D.MENGE,0)
            FROM (
              SELECT SUM( (
                  CASE
                    WHEN A.PRCTYP='D'
                      THEN 0
                    ELSE A.MENGE
                  END)) AS MENGE
                    FROM (
                      SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                          ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR,IDNRK,
                          ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK, 
                          ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                        FROM SAPHEE.ZPPT004
                        WHERE MANDT = P_MANDT
                          AND WOKNUM = P_WBS
                          AND IDNRK = P_MATNR ) AS A
                    WHERE ROWNO=1 ) AS A, (
              SELECT SUM(MENGE) AS MENGE
                FROM SAPHEE.ZMMT013
                WHERE MANDT = P_MANDT
                  AND POSID = P_POSID
                  AND MATNR = P_MATNR
                  AND DDATE = '00000000'
                  AND WERKS = '1000') AS C, (
              SELECT SUM(MENGE) AS MENGE
                FROM SAPHEE.ZMMT013
                WHERE MANDT = P_MANDT
                  AND EBELN = IN_EBELN
                  AND EBELP = IN_EBELP
                  AND DDATE = '00000000'
                  AND WERKS = '1000') AS D );
    ELSE
        SET RT = (
              SELECT VALUE(A.MENGE,0) -
                  CASE
                    WHEN A.MENGE IS NULL
                      THEN 0
                    ELSE VALUE(C.MENGE,0) + IN_MENGE
                  END + VALUE(D.MENGE,0)
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
                      END) ) AS MENGE
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
                                AND (MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,ITEM_SEQ) IN (
                                      SELECT  MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,ITEM_SEQ
                                        FROM SAPHEE.ZPPT004 AS B
                                        WHERE B.MANDT =  P_MANDT
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
                      AND DDATE = '00000000'
                      AND WERKS = '1000') AS C, (
                  SELECT SUM(MENGE) AS MENGE
                    FROM SAPHEE.ZMMT013
                    WHERE MANDT = P_MANDT
                      AND EBELN = IN_EBELN
                      AND EBELP = IN_EBELP
                      AND DDATE = '00000000'
                      AND WERKS = '1000') AS D );
    END IF;

    RETURN RT;
  END;

COMMENT ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  IS '자재발주 수량 검사(BOM과 비교)';

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO PUBLIC;







  
  
==================================테스트1====================================================
 SELECT 
   '0' AS CHECK
  , MM013.POSID AS POSID
  , MAX(MM013.POST1) AS POST1
  , (SELECT CTEXT2 FROM SAPHEE.ZLCODE WHERE MANDT = '183'  AND LANG = 'ko'  AND CODE1 = '10' 
            AND CODE2 = MAX(MM013.EKGRP)) AS EKGRP  
  , MAX(MM013.NAME1) AS NAME1
  , SAPHEE.GET_CURRDATA(MAX(MM013.WAERS),SUM(MM013.NETPR*MM013.MENGE)) AS NETPR
  , SUM(MM013.MENGE) AS MENGE
  , MAX(MM013.WAERS) AS WAERS
  , SAPHEE.GET_ZERODATE(MIN(MM013.SDATE)) AS SDATE
  , SAPHEE.GET_ZERODATE(MIN(MM013.STIME)) AS STIME
  , SAPHEE.GET_ZERODATE(MIN(MM013.DDATE)) AS DDATE
  , SAPHEE.GET_ZERODATE(MIN(MM013.DTIME)) AS DTIME
  , MAX(MM013.STATE) AS STATE
  , SAPHEE.GET_VENDER_NAME(MM013.MANDT, MAX(MM013.LIFNR)) AS RV_NAME
  , MATNR AS MATNR
  , SAPHEE.GET_ZERODATE(UDATE) AS UDATE
  , CASE WHEN SYSIBM.MIN(SAPHEE.GET_JAJEA_BALJU(MM013.MANDT, MM013.POSID1,MM013.MATNR, '', '', 0.0)) < 0 THEN '1' ELSE '0' END  AS BIGO
-- ,'0' AS BIGO
  --, CASE WHEN MIN(MM013.WERKS) <> '2000' AND MIN(SAPHEE.GET_JAJEA_BALJU(MM013.MANDT, MM013.POSID1,MM013.MATNR)) <= -1 THEN '1' ELSE '0' END  AS BIGO  --소수점 이하 변경시 오류로 수정
  , MIN(CAST(MA01.ZQNTY AS INT)) AS ZQNTY
FROM (
 	SELECT 
 	   CASE WHEN SUBSTR(POSID,1,2) = 'QM' THEN SUBSTR(POSID,1,8)
 	   			WHEN SUBSTR(POSID,1,1) = 'Q' THEN SUBSTR(POSID,1,7)
                          ELSE SUBSTR(POSID,1,6) END AS POSID
 	 , POST1 AS POST1
 	 , NAME1 AS NAME1
 	 , WAERS AS WAERS
 	 , MENGE AS MENGE
 	 , SDATE AS SDATE
 	 , STIME AS STIME
 	 , DDATE AS DDATE
 	 , DTIME AS DTIME
 	 , STATE AS STATE
 	 , MANDT AS MANDT
 	 , LIFNR AS LIFNR
 	 , MATNR AS MATNR
               , UDATE AS UDATE
               , EKGRP AS EKGRP
               , NETPR AS NETPR
               , WERKS AS WERKS
               , POSID AS POSID1               
 	FROM SAPHEE.ZMMT013 AS MM013A
 WHERE  MM013A.MANDT = '183' 
    AND MM013A.LIFNR = '2188115501'
    AND MM013A.STATE = '2'
      AND MM013A.UDATE BETWEEN '20141027' AND '20141027'
     AND MM013A.POTYPE = ''
      AND MM013A.LGORT = '1000'
 ) AS MM013
       LEFT OUTER JOIN SAPHEE.ZMASTER01 AS MA01 ON MA01.MANDT= MM013.MANDT
	                                             AND MA01.POSID = MM013.POSID
WHERE MM013.MANDT = '183' 
  GROUP BY MM013.MANDT,MM013.POSID,MM013.UDATE,MM013.MATNR
  ORDER BY POSID,UDATE,MATNR
  FOR FETCH ONLY
  WITH UR	
====================================테스트 2=====================================================

SELECT 
	 MM013.CHAR1 AS CHAR1
	, SAPHEE.GET_ZERODATE(MM013.CHDAT) AS CHDAT
	, SAPHEE.GET_ZERODATE(MM013.CHTIM) AS CHTIM
	, SAPHEE.GET_ZERODATE(MM013.DDATE) AS DDATE
	, MM013.DNAME AS DNAME
	, SAPHEE.GET_ZERODATE(MM013.DTIME) AS DTIME
	, MM013.EBELN AS EBELN
	, MM013.EBELP AS EBELP
	, SAPHEE.GET_ZERODATE(MM013.EDATE) AS EDATE
	, SAPHEE.GET_ZERODATE(MM013.EINDT) AS EINDT
	, MM013.EKGRP AS EKGRP
	, SAPHEE.GET_ZERODATE(MM013.ETIME) AS ETIME
	, MM013.EXTWG AS EXTWG
	, MM013.GROES AS GROES
	, MM013.INFNR AS INFNR
	, MM013.KNTTP AS KNTTP
	, MM013.LGOBE AS LGOBE
	, MM013.LGORT AS LGORT
	, MM013.LIFNR AS LIFNR
	, MM013.LNAME AS LNAME
	, MM013.MANDT AS MANDT
	, MM013.MATNR AS MATNR
	, VALUE(MM011.BISMT,'') AS BISMT
	, MM013.MEINS AS MEINS
	, MM013.MENGE AS MENGE
	, (MM013.MENGE - VALUE(MM017A.MENGE, 0)) AS MENGE1
	, VALUE(MM017A.B_MENGE, 0) AS B_MENGE
	, MM013.NAME1 AS NAME1
	, SAPHEE.GET_CURRDATA(MM013.WAERS,MM013.NETPR) AS NETPR
	, MM013.POSID AS POSID
	, CASE WHEN SUBSTR(MM013.POSID,1,2) = 'QM' THEN SUBSTR(MM013.POSID,1,8)
				WHEN SUBSTR(MM013.POSID,1,1) = 'Q' THEN SUBSTR(MM013.POSID,1,7)
                          ELSE SUBSTR(MM013.POSID,1,6) END  AS PROJ
	, MM013.POST1 AS POST1
	, MM013.POTYPE AS POTYPE
	, MM013.PSTYP AS PSTYP
	, SAPHEE.GET_ZERODATE(MM013.SDATE) AS SDATE
	, MM013.SNAME AS SNAME
	, MM013.STATE AS STATE
	, SAPHEE.GET_ZERODATE(MM013.STIME) AS STIME
	, MM013.SUBMI AS SUBMI
	, (MM011.MATNR_NM ||','|| MM011.ZSIZE ||','|| MM011.SPEC) AS TXZ01
	, SAPHEE.GET_ZERODATE(MM013.UDATE) AS UDATE
	, MM013.VALUE AS VALUE
	, MM013.WAERS AS WAERS
	, MM013.WERKS AS WERKS
	, MM013.ZEINR AS ZEINR
              , '0' AS CHECK
              , SAPHEE.GET_VENDER_NAME(MM013.MANDT, MM013.LIFNR) AS RV_NAME
              , (SELECT CTEXT2 FROM SAPHEE.ZLCODE WHERE MANDT = '183'  AND LANG = 'ko' AND CODE1 = '10' 
                        AND CODE2 = MM013.EKGRP) AS R_EKGRP               
              ,SAPHEE.GET_REMARK(MM013.MANDT, SUBSTR(MM013.POSID,1,9),MM013.MATNR,' | ',270) AS TEX01
             , CASE WHEN MM013.WERKS <> '2000' AND SAPHEE.GET_JAJEA_BALJU(MM013.MANDT, MM013.POSID,MM013.MATNR) < 0 THEN '1' ELSE '0' END AS BIGO
         --,'0' AS BIGO
              --, CASE WHEN MM013.WERKS <> '2000' AND SAPHEE.GET_JAJEA_BALJU(MM013.MANDT, MM013.POSID,MM013.MATNR) <= -1 THEN '1' ELSE '0' END AS BIGO   --소수점 이하변경시 오류로 수정 HSS
	, CASE WHEN SUBSTR(MM013.MATNR,1,3) = '361' THEN SAPHEE.GET_SILCHEUK(MM013.MANDT,MM013.POSID) ELSE  '' END AS SILCHEUK
              , VALUE(SAPHEE.GET_PROJ_END(MM013.MANDT,MM013.POSID ),'') AS P_END
              , VALUE(SAPHEE.GET_SEOLCHI_VEND_NAME(MM013.MANDT,MM013.POSID ),'') AS S_VEND
              , CASE WHEN MM013.BOX_CK = '24' THEN '신박스' ELSE '구박스' END AS BOXCK
 FROM SAPHEE.ZMMT013 AS MM013
 LEFT OUTER JOIN SAPHEE.ZMMT011 AS MM011 ON MM013.MANDT = MM011.MANDT AND MM013.MATNR = MM011.MATNR 
 LEFT OUTER JOIN ( 
                  SELECT 
                       MANDT
                     , EBELN
                     , EBELP 
                     ,COALESCE(SUM(MM017.MENGE - MM017.B_MENGE),0) AS MENGE
                     ,COALESCE(SUM(MM017.B_MENGE),0) AS B_MENGE
                  FROM SAPHEE.ZMMT017 AS MM017
                  WHERE MM017.MANDT = '183'
                      AND MM017.LIFNR = '2188115501'
                                  AND MM017.UDATE BETWEEN '20141025' AND '20141028'
                                  AND MM017.LGORT = '1000'
                               AND MM017.PRO_F >= '1' 
                      GROUP BY MM017.MANDT,MM017.EBELN,MM017.EBELP) AS MM017A
                  ON MM013.MANDT = MM017A.MANDT 
                  AND MM013.EBELN = MM017A.EBELN
                  AND MM013.EBELP = MM017A.EBELP
 WHERE  MM013.MANDT = '183' 
    AND MM013.LIFNR = '2188115501'
      AND MM013.UDATE BETWEEN '20141025' AND '20141125'
 
    AND MM013.STATE = '2'
    AND MM013.POTYPE = ''

      AND MM013.LGORT = '1000'
   ORDER BY EBELN,EBELP,EINDT,POSID
  FOR FETCH ONLY
  WITH UR	
  
  
  ==================================HEP  원본 ==========================================
-- Start of generated script for 203.242.37.20-DB2HEP-HEP (db2hep)
--  Oct-26-2014 at 14:26:39

DROP SPECIFIC FUNCTION "SAPHEE"."GET_JAJEA_BALJU_01";

SET SCHEMA DB2HEP;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","DB2HEP";

CREATE FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 ("IN_MANDT" VARCHAR(9),
  "IN_POSID" VARCHAR(72),
  "IN_MATNR" VARCHAR(30),
  "IN_EBELN" VARCHAR(30),
  "IN_EBELP" VARCHAR(15),
  "IN_MENGE" DECIMAL(13, 3)
 ) 
  RETURNS DECIMAL(13, 3)
  SPECIFIC "SAPHEE"."GET_JAJEA_BALJU_01"
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
    DECLARE RT DECIMAL(13, 3) DEFAULT 0.0;
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
      SELECT VALUE(A.MENGE,0) -
          CASE
            WHEN A.MENGE IS NULL
              THEN 0
            ELSE VALUE(C.MENGE,0) + IN_MENGE
          END + VALUE(D.MENGE,0)
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
              END) ) AS MENGE
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
                        AND EXISTS (
                          SELECT 'X'
                            FROM SAPHEE.ZPPT004 AS B
                            WHERE B.MANDT = P_MANDT
                              AND B.WOKNUM = P_WBS
                              AND B.IDNRK = P_MATNR
                              AND B.MATNR = A.IDNRK ) ) AS B
                  WHERE ROWNO=1 ) AS B
              ON B.MANDT = A.MANDT
              AND B.WOKNUM= A.WOKNUM
              AND B.IDNRK = A.MATNR ) AS A, (
          SELECT SUM(MENGE) AS MENGE
            FROM SAPHEE.ZMMT013
            WHERE MANDT = P_MANDT
              AND POSID = P_POSID
              AND MATNR = P_MATNR
              AND DDATE = '00000000'
              AND WERKS = '1000') AS C, (
          SELECT SUM(MENGE) AS MENGE
            FROM SAPHEE.ZMMT013
            WHERE MANDT = P_MANDT
              AND EBELN = IN_EBELN
              AND EBELP = IN_EBELP
              AND DDATE = '00000000'
              AND WERKS = '1000') AS D );
    RETURN RT;
  END;

COMMENT ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  IS '자재발주 수량 검사(BOM과 비교)';

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO PUBLIC;



-- End of generated script for 203.242.37.20-DB2HEP-HEP (db2hep)


==================================HEP 20141026 적용 수정 버전===============================================

DROP SPECIFIC FUNCTION "SAPHEE"."GET_JAJEA_BALJU_01";

SET SCHEMA DB2HEP;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","DB2HEP";

CREATE FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 ("IN_MANDT" VARCHAR(9),
  "IN_POSID" VARCHAR(72),
  "IN_MATNR" VARCHAR(30),
  "IN_EBELN" VARCHAR(30),
  "IN_EBELP" VARCHAR(15),
  "IN_MENGE" DECIMAL(13, 3)
 ) 
  RETURNS DECIMAL(13, 3)
  SPECIFIC "SAPHEE"."GET_JAJEA_BALJU_01"
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
    DECLARE RT DECIMAL(13, 3) DEFAULT 0.0;
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
          SELECT VALUE(A.MENGE,0) -
              CASE
                WHEN A.MENGE IS NULL
                  THEN 0
                ELSE VALUE(C.MENGE,0) + IN_MENGE
              END + VALUE(D.MENGE,0)
            FROM (
              SELECT SUM( (
                  CASE
                    WHEN A.PRCTYP='D'
                      THEN 0
                    ELSE A.MENGE
                  END)) AS MENGE
                    FROM (
                      SELECT MANDT ,WOKNUM ,MATNR ,IDNRK ,MENGE ,PRCTYP , 
                          ROW_NUMBER() OVER(PARTITION BY MANDT,WOKNUM,MATNR,IDNRK,
                          ITEM_SEQ,MATKL ORDER BY MANDT,WOKNUM,MATNR,IDNRK, 
                          ITEM_SEQ,CRDAT DESC,SEQNO DESC) AS ROWNO
                        FROM SAPHEE.ZPPT004
                        WHERE MANDT = P_MANDT
                          AND WOKNUM = P_WBS
                          AND IDNRK = P_MATNR ) AS A
                    WHERE ROWNO=1 ) AS A, (
              SELECT SUM(MENGE) AS MENGE
                FROM SAPHEE.ZMMT013
                WHERE MANDT = P_MANDT
                  AND POSID = P_POSID
                  AND MATNR = P_MATNR
                  AND DDATE = '00000000'
                  AND WERKS = '1000') AS C, (
              SELECT SUM(MENGE) AS MENGE
                FROM SAPHEE.ZMMT013
                WHERE MANDT = P_MANDT
                  AND EBELN = IN_EBELN
                  AND EBELP = IN_EBELP
                  AND DDATE = '00000000'
                  AND WERKS = '1000') AS D );
    ELSE
        SET RT = (
              SELECT VALUE(A.MENGE,0) -
                  CASE
                    WHEN A.MENGE IS NULL
                      THEN 0
                    ELSE VALUE(C.MENGE,0) + IN_MENGE
                  END + VALUE(D.MENGE,0)
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
                      END) ) AS MENGE
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
                                AND (MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,ITEM_SEQ) IN (
                                      SELECT  MANDT ,CRDAT,SEQNO,WOKNUM ,MATNR ,IDNRK ,MATKL,ITEM_SEQ
                                        FROM SAPHEE.ZPPT004 AS B
                                        WHERE B.MANDT =  P_MANDT
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
                      AND DDATE = '00000000'
                      AND WERKS = '1000') AS C, (
                  SELECT SUM(MENGE) AS MENGE
                    FROM SAPHEE.ZMMT013
                    WHERE MANDT = P_MANDT
                      AND EBELN = IN_EBELN
                      AND EBELP = IN_EBELP
                      AND DDATE = '00000000'
                      AND WERKS = '1000') AS D );
    END IF;

    RETURN RT;
  END;

COMMENT ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  IS '자재발주 수량 검사(BOM과 비교)';

GRANT EXECUTE ON FUNCTION "SAPHEE"."GET_JAJEA_BALJU"
 (VARCHAR(9),
  VARCHAR(72),
  VARCHAR(30),
  VARCHAR(30),
  VARCHAR(15),
  DECIMAL(13, 3)
 ) 
  TO PUBLIC;