package sosnina.camundatest.configs;

import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class CamundaConfig extends SpringProcessEngineConfiguration {

    @Bean
    @Primary
    public SpringProcessEngineConfiguration processEngineConfiguration(
            DataSourceTransactionManager transactionManager,
            DataSource dataSource,
            List<ProcessEnginePlugin> processEnginePlugins) {

        this.setHistoryLevel(HistoryLevel.HISTORY_LEVEL_FULL);
        this.setTransactionManager(transactionManager);
        this.setDatabaseSchemaUpdate("true");
        this.setDataSource(dataSource);
        this.setJobExecutorActivate(true);
        this.setProcessEnginePlugins(processEnginePlugins);
        return this;
    }

    /**
     * Не отключаем пока
     */
/*    @Bean
    public SpringProcessEngineConfiguration turnOffCamundaHistory(){
        this.getManagementService().setProperty("historyLevel", "0");
        this.setHistoryLevel(HistoryLevel.HISTORY_LEVEL_NONE);
        return this;
    }*/

    @Bean
    public SpringProcessEngineConfiguration cleanHistoryService(){

        /**
         * Вроде как тут планировалась присвоение для имеющихся данных срока хранения в 1 день
         * но оно не работает
         */
        var processDefinitionList = this.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionKey("Process_1yhdj7o")
                .list();
        for(ProcessDefinition processDefinition : processDefinitionList){
            this.repositoryService.updateProcessDefinitionHistoryTimeToLive(processDefinition.getId(), 0);
        }

        this.setHistoryCleanupBatchWindowStartTime("02:30");
        this.setHistoryCleanupBatchWindowEndTime("03:00");
        this.setHistoryTimeToLive("1");
        this.setHistoryCleanupStrategy("endTimeBased");
        return this;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}

