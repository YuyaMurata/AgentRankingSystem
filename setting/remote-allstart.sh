#確認
sshpass -p 11m35584 ssh $1@$1 'echo `hostname`'
echo "確認後 Enter"
read

#遠隔で実験用スクリプトを起動
sshpass -p 11m35584 ssh $1@$1 \
cd $CETA_HOME/App/AgentSystem/AgentSystem/setting;\
./git-remote.sh"