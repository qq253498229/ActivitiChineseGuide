FROM tomcat:8

ENV ACTIVITI_VERSION 6.0.0
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH
ENV APP_PATH $CATALINA_HOME/webapps/activiti-app

COPY activiti-$ACTIVITI_VERSION.zip .
RUN unzip activiti-$ACTIVITI_VERSION.zip \
  && mkdir -p $APP_PATH \
  && mv activiti-$ACTIVITI_VERSION/wars/activiti-app.war $APP_PATH \
  && rm -rf activiti-$ACTIVITI_VERSION activiti-$ACTIVITI_VERSION.zip \
  && cd $APP_PATH \
  && unzip activiti-app.war \
  && rm -rf activiti-app.war
#VOLUME $APP_PATH/WEB-INF/classes/META-INF/activiti-app

EXPOSE 8080

RUN cd $CATALINA_HOME/bin
CMD ["catalina.sh", "run"]

