package com.intera.roostrap.web;

import com.intera.roostrap.domain.Actionitem;
import com.intera.roostrap.domain.Agendaitem;
import com.intera.roostrap.domain.Directory;
import com.intera.roostrap.domain.Meeting;
import com.intera.roostrap.domain.Note;
import com.intera.roostrap.domain.security.Role;
import com.intera.roostrap.domain.security.User;
import com.intera.roostrap.domain.security.UserRole;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		
	}

	public Converter<Actionitem, String> getActionitemToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.intera.roostrap.domain.Actionitem, java.lang.String>() {
            public String convert(Actionitem actionitem) {
                return new StringBuilder().append(actionitem.getActionItemID()).append(' ').append(actionitem.getAciassignee()).append(' ').append(actionitem.getAcidesc()).append(' ').append(actionitem.getAcimeetingID()).toString();
            }
        };
    }

	public Converter<String, Actionitem> getIdToActionitemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.intera.roostrap.domain.Actionitem>() {
            public com.intera.roostrap.domain.Actionitem convert(java.lang.String id) {
                return Actionitem.findActionitem(id);
            }
        };
    }

	public Converter<Agendaitem, String> getAgendaitemToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.intera.roostrap.domain.Agendaitem, java.lang.String>() {
            public String convert(Agendaitem agendaitem) {
                return new StringBuilder().append(agendaitem.getAgititle()).append(' ').append(agendaitem.getAgiduration()).append(' ').append(agendaitem.getAgitype()).append(' ').append(agendaitem.getAgipresenter()).toString();
            }
        };
    }

	public Converter<String, Agendaitem> getIdToAgendaitemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.intera.roostrap.domain.Agendaitem>() {
            public com.intera.roostrap.domain.Agendaitem convert(java.lang.String id) {
                return Agendaitem.findAgendaitem(id);
            }
        };
    }

	public Converter<Directory, String> getDirectoryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.intera.roostrap.domain.Directory, java.lang.String>() {
            public String convert(Directory directory) {
                return new StringBuilder().append(directory.getPfirstName()).append(' ').append(directory.getPlastName()).append(' ').append(directory.getPemail()).append(' ').append(directory.getContactID()).toString();
            }
        };
    }

	public Converter<String, Directory> getIdToDirectoryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.intera.roostrap.domain.Directory>() {
            public com.intera.roostrap.domain.Directory convert(java.lang.String id) {
                return Directory.findDirectory(id);
            }
        };
    }

	public Converter<Meeting, String> getMeetingToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.intera.roostrap.domain.Meeting, java.lang.String>() {
            public String convert(Meeting meeting) {
                return new StringBuilder().append(meeting.getMtitle()).append(' ').append(meeting.getMdesc()).append(' ').append(meeting.getMlocation()).append(' ').append(meeting.getMeetingID()).toString();
            }
        };
    }

	public Converter<String, Meeting> getIdToMeetingConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.intera.roostrap.domain.Meeting>() {
            public com.intera.roostrap.domain.Meeting convert(java.lang.String id) {
                return Meeting.findMeeting(id);
            }
        };
    }

	public Converter<Note, String> getNoteToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.intera.roostrap.domain.Note, java.lang.String>() {
            public String convert(Note note) {
                return new StringBuilder().append(note.getNtext()).append(' ').append(note.getNcreatedBy()).append(' ').append(note.getNoteID()).append(' ').append(note.getNcreatedOn()).toString();
            }
        };
    }

	public Converter<String, Note> getIdToNoteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.intera.roostrap.domain.Note>() {
            public com.intera.roostrap.domain.Note convert(java.lang.String id) {
                return Note.findNote(id);
            }
        };
    }

	public Converter<Role, String> getRoleToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.intera.roostrap.domain.security.Role, java.lang.String>() {
            public String convert(Role role) {
                return new StringBuilder().append(role.getName()).append(' ').append(role.getDescription()).toString();
            }
        };
    }

	public Converter<Long, Role> getIdToRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.intera.roostrap.domain.security.Role>() {
            public com.intera.roostrap.domain.security.Role convert(java.lang.Long id) {
                return Role.findRole(id);
            }
        };
    }

	public Converter<String, Role> getStringToRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.intera.roostrap.domain.security.Role>() {
            public com.intera.roostrap.domain.security.Role convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Role.class);
            }
        };
    }

	public Converter<User, String> getUserToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.intera.roostrap.domain.security.User, java.lang.String>() {
            public String convert(User user) {
                return new StringBuilder().append(user.getFirstName()).append(' ').append(user.getLastName()).append(' ').append(user.getEmailAddress()).append(' ').append(user.getPassword()).toString();
            }
        };
    }

	public Converter<Long, User> getIdToUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.intera.roostrap.domain.security.User>() {
            public com.intera.roostrap.domain.security.User convert(java.lang.Long id) {
                return User.findUser(id);
            }
        };
    }

	public Converter<String, User> getStringToUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.intera.roostrap.domain.security.User>() {
            public com.intera.roostrap.domain.security.User convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), User.class);
            }
        };
    }

	public Converter<UserRole, String> getUserRoleToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.intera.roostrap.domain.security.UserRole, java.lang.String>() {
            public String convert(UserRole userRole) {
                return "(no displayable fields)";
            }
        };
    }

	public Converter<Long, UserRole> getIdToUserRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.intera.roostrap.domain.security.UserRole>() {
            public com.intera.roostrap.domain.security.UserRole convert(java.lang.Long id) {
                return UserRole.findUserRole(id);
            }
        };
    }

	public Converter<String, UserRole> getStringToUserRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.intera.roostrap.domain.security.UserRole>() {
            public com.intera.roostrap.domain.security.UserRole convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UserRole.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getActionitemToStringConverter());
        registry.addConverter(getIdToActionitemConverter());
        registry.addConverter(getAgendaitemToStringConverter());
        registry.addConverter(getIdToAgendaitemConverter());
        registry.addConverter(getDirectoryToStringConverter());
        registry.addConverter(getIdToDirectoryConverter());
        registry.addConverter(getMeetingToStringConverter());
        registry.addConverter(getIdToMeetingConverter());
        registry.addConverter(getNoteToStringConverter());
        registry.addConverter(getIdToNoteConverter());
        registry.addConverter(getRoleToStringConverter());
        registry.addConverter(getIdToRoleConverter());
        registry.addConverter(getStringToRoleConverter());
        registry.addConverter(getUserToStringConverter());
        registry.addConverter(getIdToUserConverter());
        registry.addConverter(getStringToUserConverter());
        registry.addConverter(getUserRoleToStringConverter());
        registry.addConverter(getIdToUserRoleConverter());
        registry.addConverter(getStringToUserRoleConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
