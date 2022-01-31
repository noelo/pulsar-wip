# Simple Pulsar Producer/Consumer

Helm Install
````
helm repo add apachepulsar https://pulsar.apache.org/charts
helm repo update
helm show values apachepulsar/pulsar > pulsar-values.yaml
````
Edit pulsar-values as needed
````

oc adm policy add-scc-to-user anyuid -z default -z pulsar-broker-acct -z pulsar-functions-worker -z pulsar-prometheus -z pulsar-bookie -z pulsar-zookeeper -z pulsar-proxy -n pulsar
helm install pulsar apachepulsar/pulsar -f pulsar-values.yaml --timeout 10m
````

OC Commands
````
oc expose svc pulsar-grafana
oc get secret -o yaml pulsar-grafana-secret
oc expose svc pulsar-pulsar-manager
oc create route passthrough sslproxy-route --service=pulsar-proxy --port=6651
````

Run Java client
````
mvn exec:java -Dexec.args="Producer"

mvn exec:java -Dexec.args="Consumer"
````

