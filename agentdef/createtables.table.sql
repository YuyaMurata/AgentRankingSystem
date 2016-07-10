-- -----------------------------
-- create table of ranksys
-- -----------------------------

--		<!--UserAgent Entity define-->
--		<entity type="useragent">
--			<attribute name="UserID" type="string" primarykey="true" maxlength="16"/>
--			<attribute name="Profile" type="profile" singleentity="true"/>
--			<attribute name="Data" type="long" />
--			<attribute name="ConnectionCount" type="long" />
--			<attribute name="MessageLatency" type="long" />
--                      <attribute name="MessageQueueLength" type="long" />
--			<attribute name="Log" type="userlog" />
--		</entity>
--		<entity type="profile">
--			<attribute name="UserID" type="string" primarykey="true" relationto="UserID" maxlength="16"/>
--			<attribute name="Name" type="string" maxlength="32"/>
--			<attribute name="Sex" type="string" maxlength="2"/>
--			<attribute name="Age" type="string" maxlength="4"/>
--			<attribute name="Address" type="string" maxlength="64"/>
--			<attribute name="LastAccessTime" type="timestamp" />
--		</entity>
--		<entity type="userlog">
--			<attribute name="UserID" type="string" primarykey="true" relationto="UserID" maxlength="16"/>
--			<attribute name="AccessID" type="string" primarykey="true" maxlength="16"/>
--			<attribute name="CurrentTime" type="long"/>
--			<attribute name="LastAccessTime" type="timestamp" />
--		</entity>

CREATE TRANSIENT TABLE useragent (
	UserID VARCHAR(16) NOT NULL,
	Data INTEGER,
	ConnectionCount BIGINT,
	MessageLatency BIGINT,
        MessageQueueLength BIGINT,
	PRIMARY KEY(UserID)
);
COMMIT WORK;

CREATE TRANSIENT TABLE profile(
	UserID VARCHAR(16) NOT NULL,
	Name WVARCHAR(64),
	Sex WVARCHAR(4),
	Age WVARCHAR(8),
	Address WVARCHAR(128),
	LastAccessTime TIMESTAMP,
	PRIMARY KEY(UserID)
);
COMMIT WORK;

CREATE TRANSIENT TABLE userlog(
	UserID VARCHAR(16) NOT NULL,
	AccessID VARCHAR(16) NOT NULL,
	LastAccessTime TIMESTAMP,
	CurrentTime BIGINT,
	PRIMARY KEY(UserID, AccessID)
);
COMMIT WORK;


--              <!--RankAgent Entity define-->
--		<entity type="rankagent">
--			<attribute name="RankID" type="string" primarykey="true" maxlength="16"/>
--			<attribute name="RankTable" type="ranktable" />
--                      <attribute name="TotalUsers" type="long" />
--			<attribute name="ConnectionCount" type="long" />
--      <attribute name="MessageLatency" type="long" />
--      <attribute name="MessageQueueLength" type="long" />
--			<attribute name="Log" type="ranklog" />
--		</entity>
--              <entity type="ranktable">
--			<attribute name="RankID" type="string" primarykey="true" relationto="RankID" maxlength="16"/>
--			<attribute name="UserID" type="string" primarykey="true" maxlength="16"/>
--			<attribute name="Rank" type="long" />
--                      <attribute name="Data" type="long" />
--                      <attribute name="ConnectionCount" type="long" />
--	<attribute name="CurrentTime" type="long"/>
--                      <attribute name="LastAccessTime" type="timestamp" />
--		</entity>
--		<entity type="ranklog">
--			<attribute name="RankID" type="string" primarykey="true" relationto="RankID" maxlength="16"/>
--			<attribute name="AccessID" type="string" primarykey="true" maxlength="16"/>
--	<attribute name="CurrentTime" type="long"/>
--			<attribute name="LastAccessTime" type="timestamp" />
--		</entity>

CREATE TRANSIENT TABLE rankagent (
	RankID VARCHAR(16) NOT NULL,
	TotalUsers BIGINT,
	ConnectionCount BIGINT,
	MessageLatency BIGINT,
        MessageQueueLength BIGINT,
	PRIMARY KEY(RankID)
);
COMMIT WORK;

CREATE TRANSIENT TABLE ranktable(
	RankID VARCHAR(16) NOT NULL,
        UserID VARCHAR(16) NOT NULL,
	Rank BIGINT,
	Data BIGINT,
	ConnectionCount BIGINT,
	CurrentTime BIGINT,
	LastAccessTime TIMESTAMP,
	PRIMARY KEY(RankID, UserID)
);
COMMIT WORK;

CREATE TRANSIENT TABLE ranklog(
	RankID VARCHAR(16) NOT NULL,
	AccessID VARCHAR(16) NOT NULL,
	CurrentTime BIGINT,
	LastAccessTime TIMESTAMP,
	PRIMARY KEY(RankID, AccessID)
);
COMMIT WORK;