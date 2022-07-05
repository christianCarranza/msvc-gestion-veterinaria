CREATE TABLE CATEGORIAS(
                           ID_CATEGORIA            NUMERIC(6,0) NOT NULL PRIMARY KEY,
                           DESCRIPCION             VARCHAR(45) NOT NULL,
                           ESTADO                  INT NOT NULL,
                           FECHA_CREACION          DATE NOT NULL,
                           USUARIO_CREACION        NUMERIC(6,0) NOT NULL,
                           FECHA_MODIFICACION      DATE NULL,
                           USUARIO_MODIFICACION    NUMERIC(6,0) NULL
);
CREATE SEQUENCE SEQ_CATEGORIAS;

CREATE TABLE PRODUCTOS(
                          ID_PRODUCTO NUMERIC(6,0) NOT NULL PRIMARY KEY,
                          ID_CATEGORIA_FK NUMERIC(6,0),
                          NOMBRE VARCHAR2(120) NOT NULL UNIQUE,
                          DESCRIPCION VARCHAR2(2000) NULL,
                          PRECIO NUMERIC(6,2) NOT NULL,
                          STOCK NUMERIC(4,0) NOT NULL,
                          ESTADO NUMERIC NOT NULL,
                          FECHA_CREACION          DATE NOT NULL,
                          USUARIO_CREACION        NUMERIC(6,0) NOT NULL,
                          FECHA_MODIFICACION      DATE NULL,
                          USUARIO_MODIFICACION    NUMERIC(6,0) NULL
);

CREATE SEQUENCE SEQ_PRODUCTOS;


INSERT INTO CATEGORIAS(ID_CATEGORIA,DESCRIPCION,ESTADO,FECHA_CREACION,USUARIO_CREACION,FECHA_MODIFICACION,USUARIO_MODIFICACION)
VALUES(SEQ_CATEGORIAS.NEXTVAL, 'Alimentación', 1, TO_DATE('2022/06/23 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),1,null,null);

INSERT INTO CATEGORIAS(ID_CATEGORIA,DESCRIPCION,ESTADO,FECHA_CREACION,USUARIO_CREACION,FECHA_MODIFICACION,USUARIO_MODIFICACION)
VALUES(SEQ_CATEGORIAS.NEXTVAL, 'Accesorios para mascotas', 1, TO_DATE('2022/07/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),1,null,null);

INSERT INTO CATEGORIAS(ID_CATEGORIA,DESCRIPCION,ESTADO,FECHA_CREACION,USUARIO_CREACION,FECHA_MODIFICACION,USUARIO_MODIFICACION)
VALUES(SEQ_CATEGORIAS.NEXTVAL, 'Material veterinario', 1, TO_DATE('2022/07/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),1,null,null);




INSERT INTO PRODUCTOS(ID_PRODUCTO,ID_CATEGORIA_FK,NOMBRE,DESCRIPCION,PRECIO,STOCK,ESTADO,FECHA_CREACION,USUARIO_CREACION,FECHA_MODIFICACION,USUARIO_MODIFICACION)
VALUES(SEQ_PRODUCTOS.NEXTVAL, 1, 'Lorem Ipsum','Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC',30,80,1,TO_DATE('2022/07/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),1,null,null);

INSERT INTO PRODUCTOS(ID_PRODUCTO,ID_CATEGORIA_FK,NOMBRE,DESCRIPCION,PRECIO,STOCK,ESTADO,FECHA_CREACION,USUARIO_CREACION,FECHA_MODIFICACION,USUARIO_MODIFICACION)
VALUES(SEQ_PRODUCTOS.NEXTVAL, 2, 'Lorem Ipsum 2','There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form',80,50,1,TO_DATE('2022/07/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),1,null,null);

INSERT INTO PRODUCTOS(ID_PRODUCTO,ID_CATEGORIA_FK,NOMBRE,DESCRIPCION,PRECIO,STOCK,ESTADO,FECHA_CREACION,USUARIO_CREACION,FECHA_MODIFICACION,USUARIO_MODIFICACION)
VALUES(SEQ_PRODUCTOS.NEXTVAL, 3, 'Lorem Ipsum 3','If you are going to use a passage of Lorem Ipsum, you need to be sure there isnt anything embarrassing hidden in the middle of text',10,70,1,TO_DATE('2022/07/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),1,null,null);

INSERT INTO PRODUCTOS(ID_PRODUCTO,ID_CATEGORIA_FK,NOMBRE,DESCRIPCION,PRECIO,STOCK,ESTADO,FECHA_CREACION,USUARIO_CREACION,FECHA_MODIFICACION,USUARIO_MODIFICACION)
VALUES(SEQ_PRODUCTOS.NEXTVAL, 1, 'Lorem Ipsum 1','All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.',130,20,1,TO_DATE('2022/07/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),1,null,null);


COMMIT;

SELECT * FROM PRODUCTOS;

SELECT * FROM CATEGORIAS;








