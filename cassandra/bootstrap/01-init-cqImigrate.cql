CREATE KEYSPACE IF NOT EXISTS security WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };

CREATE TABLE security.user (username text PRIMARY KEY, password text
) WITH bloom_filter_fp_chance = 0.1
    AND caching = {'keys':'ALL', 'rows_per_partition':'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.LeveledCompactionStrategy'}
    AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.SnappyCompressor'}
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99.0PERCENTILE';


INSERT INTO security.user(username,password) VALUES ('username1','password1');
INSERT INTO security.user(username,password) VALUES ('username2','password2');
INSERT INTO security.user(username,password) VALUES ('username3','password3');
