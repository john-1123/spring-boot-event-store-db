package com.example.springbooteventstoredb.accounts.gettingbyid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, UUID> {
    @Query("SELECT d FROM AccountDetails d WHERE d.id = ?1 AND d.version > ?2")
    Optional<AccountDetails> findByIdAndNeverVersion(UUID id, long version);
}
