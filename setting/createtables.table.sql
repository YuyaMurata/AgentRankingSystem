-- -----------------------------
-- create table of ranksys
-- -----------------------------

--		<!--UserAgent Entity define-->
--		<entity type="useragent">
--			<attribute name="UserID" type="string" primarykey="true" maxlength="16"/>
--			<attribute name="Profile" type="profile" singleentity="true"/>
--			<attribute name="Data" type="long" />
--			<attribute name="ConnectionCount" type="long" />
--			<attribute name="Log" type="log" />
--		</entity>
--		<entity type="profile">
--			<attribute name="UserID" type="string" primarykey="true" relationto="UserID" maxlength="16"/>
--			<attribute name="Name" type="string" maxlength="32"/>
--			<attribute name="Sex" type="string" maxlength="2"/>
--			<attribute name="Age" type="string" maxlength="4"/>
--			<attribute name="Address" type="string" maxlength="64"/>
--			<attribute name="LastAccessTime" type="timestamp" />
--		</entity>
--		<entity type="log">
--			<attribute name="UserID" type="string" primarykey="true" relationto="UserID" maxlength="16"/>
--			<attribute name="AccessID" type="string" primarykey="true" maxlength="16"/>
--			<attribute name="LastAccessTime" type="timestamp" />
--		</entity>

CREATE TRANSIENT TABLE useragent (
	UserID VARCHAR(64) NOT NULL,
	Data BIGINT,
	ConnectionCount BIGINT,
	PRIMARY KEY(UserID)
);
COMMIT WORK;

CREATE TRANSIENT TABLE profile(
	UserID VARCHAR(64) NOT NULL,
	Name WVARCHAR(64),
	Sex WVARCHAR(4),
	Age WVARCHAR(8),
	Address WVARCHAR(128),
	LastAccessTime TIMESTAMP,
	PRIMARY KEY(UserID)
);
COMMIT WORK;

CREATE TRANSIENT TABLE log(
	UserID VARCHAR(64) NOT NULL,
	AccessID VARCHAR(16) NOT NULL,
        CurrentTime BIGINT,
	LastAccessTime TIMESTAMP,
	PRIMARY KEY(UserID, AccessID)
);
COMMIT WORK;

