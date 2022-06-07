package uz.pdp.online.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class AuditAwareConfig {
    AuditorAware<UUID> auditorAware(){
        return new AAware();
    }
}
