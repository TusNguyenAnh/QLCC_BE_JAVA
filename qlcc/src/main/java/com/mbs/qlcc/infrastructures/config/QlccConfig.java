package com.mbs.qlcc.infrastructures.config;

import com.mbs.qlcc.adapters.db.Authentication.*;
import com.mbs.qlcc.adapters.db.Apartment.JpaApartment;
import com.mbs.qlcc.adapters.db.Apartment.JpaApartmentRepository;
import com.mbs.qlcc.adapters.db.Building.JpaBuilding;
import com.mbs.qlcc.adapters.db.Building.JpaBuildingRepository;
import com.mbs.qlcc.adapters.db.Complex.JpaComplex;
import com.mbs.qlcc.adapters.db.Complex.JpaComplexRepository;
import com.mbs.qlcc.adapters.db.MediaFile.JpaMediaFile;
import com.mbs.qlcc.adapters.db.MediaFile.JpaMediaFileRepository;
import com.mbs.qlcc.adapters.db.Organization.*;
import com.mbs.qlcc.adapters.db.Resident.JpaResident;
import com.mbs.qlcc.adapters.db.Resident.JpaResidentRepository;
import com.mbs.qlcc.adapters.db.Resident.JpaAptResident;
import com.mbs.qlcc.adapters.db.Resident.JpaAptResidentRepository;
import com.mbs.qlcc.adapters.db.Token.JpaToken;
import com.mbs.qlcc.adapters.db.Token.JpaTokenRepository;
import com.mbs.qlcc.adapters.db.User.JpaUser;
import com.mbs.qlcc.adapters.db.User.JpaUserRepository;
import com.mbs.qlcc.adapters.services.EmailService;
import com.mbs.qlcc.entities.Apartment.ApartmentFactory;
import com.mbs.qlcc.entities.Apartment.IApartmentFactory;
import com.mbs.qlcc.entities.Authentication.*;
import com.mbs.qlcc.entities.Building.BuildingFactory;
import com.mbs.qlcc.entities.Building.IBuildingFactory;
import com.mbs.qlcc.entities.Complex.ComplexFactory;
import com.mbs.qlcc.entities.Complex.IComplexFactory;
import com.mbs.qlcc.entities.MediaFile.IMediaFileFactory;
import com.mbs.qlcc.entities.MediaFile.MediaFileFactory;
import com.mbs.qlcc.entities.Organization.IOrganizationFactory;
import com.mbs.qlcc.entities.Organization.OrganizationFactory;
import com.mbs.qlcc.entities.Resident.IResidentFactory;
import com.mbs.qlcc.entities.Resident.ResidentFactory;
import com.mbs.qlcc.entities.Resident.IAptResidentFactory;
import com.mbs.qlcc.entities.Resident.AptResidentFactory;
import com.mbs.qlcc.entities.User.IUserFactory;
import com.mbs.qlcc.entities.User.UserFactory;
import com.mbs.qlcc.usecases.input.*;
import com.mbs.qlcc.usecases.interactor.*;
import com.mbs.qlcc.usecases.output.Apartment.IApartmentDsGateway;
import com.mbs.qlcc.usecases.output.Authentication.IPermissionDsGateway;
import com.mbs.qlcc.usecases.output.Authentication.IRolePermissionDsGateway;
import com.mbs.qlcc.usecases.output.Building.IBuildingDsGateway;
import com.mbs.qlcc.usecases.output.Complex.IComplexDsGateway;
import com.mbs.qlcc.usecases.output.Email.IEmailDsGateway;
import com.mbs.qlcc.usecases.output.MediaFile.IMediaFileDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrgBuildingDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrgUserDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrganizationDsGateway;
import com.mbs.qlcc.usecases.output.Resident.IResidentDsGateway;
import com.mbs.qlcc.usecases.output.Resident.IAptResidentDsGateway;
import com.mbs.qlcc.usecases.output.Role.IRoleDsGateway;
import com.mbs.qlcc.usecases.output.User.ITokenDsGateway;
import com.mbs.qlcc.usecases.output.User.IUserDsGateway;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.TemplateEngine;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class QlccConfig {
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.validDuration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshableDuration}")
    protected long REFRESHABLE_DURATION;

    @Bean("mailExecutor")
    public Executor mailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.initialize();
        return executor;
    }

    @Bean
    public IEmailDsGateway emailDsGateway(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        return new EmailService(templateEngine, javaMailSender);
    }

    @Bean
    public IComplexDsGateway complexDsGateway(JpaComplexRepository repository) {
        return new JpaComplex(repository);
    }

    public IRoleDsGateway roleDsGateway(JpaRoleRepository repository) {
        return new JpaRole(repository);
    }

    @Bean
    public IComplexFactory complexFactory() {
        return new ComplexFactory();
    }

    @Bean
    public IComplexInputBoundary complexInputBoundary(IComplexDsGateway complexDsGateway, IComplexFactory complexFactory, IEmailDsGateway emailDsGateway, IUserDsGateway userDsGateway,
                                                      ITokenDsGateway tokenDsGateway, IRoleDsGateway roleDsGateway, IOrgUserDsGateway orgUserDsGateway
    ) {
        return new ComplexInteractor(complexDsGateway, complexFactory, emailDsGateway, userDsGateway, tokenDsGateway, roleDsGateway, orgUserDsGateway);
    }

    @Bean
    public IUserDsGateway userDsGateway(JpaUserRepository repository) {
        return new JpaUser(repository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public ITokenDsGateway tokenDsGateway(JpaTokenRepository repository, JpaUserRepository jpaUserRepository, PasswordEncoder passwordEncoder) {
        return new JpaToken(repository, jpaUserRepository, passwordEncoder);
    }

    @Bean
    public IOrgUserDsGateway orgUserDsGateway(JpaOrgUserRepository repository) {
        return new JpaOrgUser(repository);
    }

    @Bean
    public IRolePermissionDsGateway rolePermissionDsGateway(JpaRolePermissionRepository repository) {
        return new JpaRolePermission(repository);
    }

    @Bean
    public IPermissionDsGateway permissionDsGateway(JpaPermissionRepository repository) {
        return new JpaPermission(repository);
    }

    @Bean
    public IRoleFactory roleFactory() {
        return new RoleFactory();
    }

    @Bean
    public IRoleInputBoundary roleInputBoundary(
            IRoleDsGateway roleDsGateway, IOrgUserDsGateway orgUserDsGateway, IRoleFactory roleFactory
    ) {
        return new RoleInteractor(roleDsGateway, orgUserDsGateway, roleFactory);
    }

    @Bean
    public IPermissionFactory permissionFactory() {
        return new PermissionFactory();
    }

    @Bean
    public IRolePermissionFactory rolePermissionFactory() {
        return new RolePermissionFactory();
    }

    @Bean
    public IPermissionInputBoundary permissionInputBoundary(
            IPermissionFactory permissionFactory, IPermissionDsGateway permissionDsGateway,
            IRolePermissionFactory rolePermissionFactory, IRolePermissionDsGateway rolePermissionDsGateway
    ) {
        return new PermissionInteractor(permissionFactory, permissionDsGateway, rolePermissionFactory, rolePermissionDsGateway);
    }


    @Bean
    public IUserFactory userFactory() {
        return new UserFactory();
    }

    @Bean
    public IAuthenticationInputBoundary authenticationInputBoundary(IUserDsGateway userDsGateway, ITokenDsGateway tokenDsGateway,
                                                                    IOrgUserDsGateway orgUserDsGateway, IRolePermissionDsGateway rolePermissionDsGateway,
                                                                    IComplexFactory cplFactory, IUserFactory userFactory) {
        return new AuthenticationInteractor(
                SIGNER_KEY, VALID_DURATION, REFRESHABLE_DURATION,
                userDsGateway, tokenDsGateway,
                userFactory, cplFactory,
                orgUserDsGateway, rolePermissionDsGateway);
    }

    @Bean
    public IUserInputBoundary userInputBoundary(IComplexDsGateway complexDsGateway, IUserDsGateway userDsGateway, ITokenDsGateway tokenDsGateway) {
        return new UserInteractor(userDsGateway, complexDsGateway, tokenDsGateway);
    }

    // Organization Configuration
    @Bean
    public IOrganizationFactory organizationFactory() {
        return new OrganizationFactory();
    }

    @Bean
    public IOrganizationDsGateway organizationDsGateway(JpaOrganizationRepository repository) {
        return new JpaOrganization(repository);
    }

    @Bean
    public IOrgBuildingDsGateway orgBuildingDsGateway(JpaOrgBuildingRepository repository) {
        return new JpaOrgBuilding(repository);
    }

    @Bean
    public IOrganizationInputBoundary organizationInputBoundary(
            IOrganizationDsGateway organizationDsGateway,
            IOrgBuildingDsGateway orgBuildingDsGateway,
            IOrganizationFactory organizationFactory
    ) {
        return new OrganizationInteractor(
                organizationDsGateway,
                orgBuildingDsGateway,
                organizationFactory
        );
    }

    // Building Configuration
    @Bean
    public IBuildingDsGateway buildingDsGateway(JpaBuildingRepository repository) {
        return new JpaBuilding(repository);
    }

    @Bean
    public IBuildingFactory buildingFactory() {
        return new BuildingFactory();
    }

    @Bean
    public IBuildingInputBoundary buildingInputBoundary(IBuildingDsGateway buildingDsGateway, IBuildingFactory buildingFactory) {
        return new BuildingInteractor(buildingDsGateway, buildingFactory);
    }

    @Bean
    public IMediaFileDsGateway mediaFileDsGateway(JpaMediaFileRepository repository) {
        return new JpaMediaFile(repository);
    }

    @Bean
    public IMediaFileFactory mediaFileFactory() {
        return new MediaFileFactory();
    }

    @Bean
    public IMediaFileInputBoundary mediaFileInputBoundary(IMediaFileDsGateway mediaFileDsGateway, IMediaFileFactory mediaFileFactory) {
        return new MediaFileInteractor(mediaFileDsGateway, mediaFileFactory);
    }

    // Apartment Configuration
    @Bean
    public IApartmentDsGateway apartmentDsGateway(JpaApartmentRepository repository) {
        return new JpaApartment(repository);
    }

    @Bean
    public IApartmentFactory apartmentFactory() {
        return new ApartmentFactory();
    }

    @Bean
    public IApartmentInputBoundary apartmentInputBoundary(
            IApartmentDsGateway apartmentDsGateway,
            IBuildingDsGateway buildingDsGateway,
            IApartmentFactory apartmentFactory
    ) {
        return new ApartmentInteractor(apartmentDsGateway, buildingDsGateway, apartmentFactory);
    }

    // Resident Configuration
    @Bean
    public IResidentDsGateway residentDsGateway(JpaResidentRepository repository) {
        return new JpaResident(repository);
    }

    @Bean
    public IAptResidentDsGateway aptResidentDsGateway(JpaAptResidentRepository repository, JpaApartmentRepository apartmentRepository, JpaResidentRepository residentRepository) {
        return new JpaAptResident(repository, apartmentRepository, residentRepository);
    }

    @Bean
    public IResidentFactory residentFactory() {
        return new ResidentFactory();
    }

    @Bean
    public IAptResidentFactory aptResidentFactory() {
        return new AptResidentFactory();
    }

    @Bean
    public IResidentInputBoundary residentInputBoundary(
            IResidentDsGateway residentDsGateway, IAptResidentDsGateway aptResidentDsGateway,
            IBuildingDsGateway buildingDsGateway, IApartmentDsGateway apartmentDsGateway,
            IOrgUserDsGateway orgUserDsGateway, IResidentFactory residentFactory, IAptResidentFactory aptResidentFactory
    ) {
        return new ResidentInteractor(
                residentDsGateway, aptResidentDsGateway,
                buildingDsGateway, apartmentDsGateway,
                orgUserDsGateway, residentFactory, aptResidentFactory
        );
    }
}
